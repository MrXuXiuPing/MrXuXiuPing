//package com.mallplus.canal.client;
//
//import com.alibaba.otter.canal.client.CanalConnector;
//import com.alibaba.otter.canal.client.CanalConnectors;
//import com.alibaba.otter.canal.protocol.CanalEntry.*;
//import com.alibaba.otter.canal.protocol.Message;
//import com.google.protobuf.InvalidProtocolBufferException;
//import org.apache.commons.dbutils.DbUtils;
//import org.apache.commons.dbutils.QueryRunner;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import javax.sql.DataSource;
//import java.net.InetSocketAddress;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.Queue;
//import java.util.concurrent.ConcurrentLinkedQueue;
//
//@Component
//public class CanalClient implements InitializingBean {
//    private final static int BATCH_SIZE = 1000;
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        // 创建链接
//        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress("172.31.251.39",
//                11111), "example", "", "");
////        CanalConnector connector = CanalConnectors.newClusterConnector("192.168.0.105:2181", "canalclient1", "", "");
//        try {
//            //打开连接
//            connector.connect();
//            //订阅数据库表,全部表
//            connector.subscribe(".*\\..*");
//            //回滚到未进行ack的地方，下次fetch的时候，可以从最后一个没有ack的地方开始拿
//            connector.rollback();
//            while (true) {
//                // 获取指定数量的数据
//                Message message = connector.getWithoutAck(BATCH_SIZE);
//                //获取批量ID
//                long batchId = message.getId();
//                //获取批量的数量
//                int size = message.getEntries().size();
//                //如果没有数据
//                if (batchId == -1 || size == 0) {
//                    try {
//                        //线程休眠2秒
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    //如果有数据,处理数据
//                    printEntry(message.getEntries());
//                }
//                //进行 batch id 的确认。确认之后，小于等于此 batchId 的 Message 都会被确认。
//                connector.ack(batchId);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            connector.disconnect();
//        }
//    }
//
//    /**
//     * 打印canal server解析binlog获得的实体类信息
//     */
//    private static void printEntry(List<Entry> entrys) {
//        for (Entry entry : entrys) {
//            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
//                //开启/关闭事务的实体类型，跳过
//                continue;
//            }
//            //RowChange对象，包含了一行数据变化的所有特征
//            //比如isDdl 是否是ddl变更操作 sql 具体的ddl sql beforeColumns afterColumns 变更前后的数据字段等等
//            RowChange rowChage;
//            try {
//                rowChage = RowChange.parseFrom(entry.getStoreValue());
//            } catch (Exception e) {
//                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(), e);
//            }
//            //获取操作类型：insert/update/delete类型
//            EventType eventType = rowChage.getEventType();
//            //打印Header信息
//            System.out.println(String.format("================》; binlog[%s:%s] , name[%s,%s] , eventType : %s",
//                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
//                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
//                    eventType));
//            //判断是否是DDL语句
//            if (rowChage.getIsDdl()) {
//                System.out.println("================》;isDdl: true,sql:" + rowChage.getSql());
//            }
//            //获取RowChange对象里的每一行数据，打印出来
//            for (RowData rowData : rowChage.getRowDatasList()) {
//                //如果是删除语句
//                if (eventType == EventType.DELETE) {
//                    printColumn(rowData.getBeforeColumnsList());
//                    //如果是新增语句
//                } else if (eventType == EventType.INSERT) {
//                    printColumn(rowData.getAfterColumnsList());
//                    //如果是更新的语句
//                } else {
//                    //变更前的数据
//                    System.out.println("------->; before");
//                    printColumn(rowData.getBeforeColumnsList());
//                    //变更后的数据
//                    System.out.println("------->; after");
//                    printColumn(rowData.getAfterColumnsList());
//                }
//            }
//        }
//    }
//
//    private static void printColumn(List<Column> columns) {
//        for (Column column : columns) {
//            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
//        }
//    }
//
//    //sql队列
//    private Queue<String> SQL_QUEUE = new ConcurrentLinkedQueue<>();
//
//    @Resource
//    private DataSource dataSource;
//
//    /**
//     * canal入库方法
//     */
//    public void run() {
//
//        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress("127.0.0.1",
//                11111), "example", "", "");
//        try {
//            connector.connect();
//            connector.subscribe(".*\\..*");
//            connector.rollback();
//            try {
//                while (true) {
//                    //尝试从master那边拉去数据batchSize条记录，有多少取多少
//                    Message message = connector.getWithoutAck(BATCH_SIZE);
//                    long batchId = message.getId();
//                    int size = message.getEntries().size();
//                    if (batchId == -1 || size == 0) {
//                        Thread.sleep(1000);
//                    } else {
//                        dataHandle(message.getEntries());
//                    }
//                    connector.ack(batchId);
//
//                    //当队列里面堆积的sql大于一定数值的时候就模拟执行
//                    if (SQL_QUEUE.size() >= 1) {
//                        executeQueueSql();
//                    }
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (InvalidProtocolBufferException e) {
//                e.printStackTrace();
//            }
//        } finally {
//            connector.disconnect();
//        }
//    }
//
//    /**
//     * 模拟执行队列里面的sql语句
//     */
//    public void executeQueueSql() {
//        int size = SQL_QUEUE.size();
//        for (int i = 0; i < size; i++) {
//            String sql = SQL_QUEUE.poll();
//            System.out.println("[sql]----> " + sql);
//
//            this.execute(sql.toString());
//        }
//    }
//
//    /**
//     * 数据处理
//     *
//     * @param entrys
//     */
//    private void dataHandle(List<Entry> entrys) throws InvalidProtocolBufferException {
//        for (Entry entry : entrys) {
//            if (EntryType.ROWDATA == entry.getEntryType()) {
//                RowChange rowChange = RowChange.parseFrom(entry.getStoreValue());
//                EventType eventType = rowChange.getEventType();
//                if (eventType == EventType.DELETE) {
//                    saveDeleteSql(entry);
//                } else if (eventType == EventType.UPDATE) {
//                    saveUpdateSql(entry);
//                } else if (eventType == EventType.INSERT) {
//                    saveInsertSql(entry);
//                }
//            }
//        }
//    }
//
//    /**
//     * 保存更新语句
//     *
//     * @param entry
//     */
//    private void saveUpdateSql(Entry entry) {
//        try {
//            RowChange rowChange = RowChange.parseFrom(entry.getStoreValue());
//            List<RowData> rowDatasList = rowChange.getRowDatasList();
//            for (RowData rowData : rowDatasList) {
//                List<Column> newColumnList = rowData.getAfterColumnsList();
//                StringBuffer sql = new StringBuffer("update " + entry.getHeader().getTableName() + " set ");
//                for (int i = 0; i < newColumnList.size(); i++) {
//                    sql.append(" " + newColumnList.get(i).getName()
//                            + " = '" + newColumnList.get(i).getValue() + "'");
//                    if (i != newColumnList.size() - 1) {
//                        sql.append(",");
//                    }
//                }
//                sql.append(" where ");
//                List<Column> oldColumnList = rowData.getBeforeColumnsList();
//                for (Column column : oldColumnList) {
//                    if (column.getIsKey()) {
//                        //暂时只支持单一主键
//                        sql.append(column.getName() + "=" + column.getValue());
//                        break;
//                    }
//                }
//                SQL_QUEUE.add(sql.toString());
//            }
//        } catch (InvalidProtocolBufferException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 保存删除语句
//     *
//     * @param entry
//     */
//    private void saveDeleteSql(Entry entry) {
//        try {
//            RowChange rowChange = RowChange.parseFrom(entry.getStoreValue());
//            List<RowData> rowDatasList = rowChange.getRowDatasList();
//            for (RowData rowData : rowDatasList) {
//                List<Column> columnList = rowData.getBeforeColumnsList();
//                StringBuffer sql = new StringBuffer("delete from " + entry.getHeader().getTableName() + " where ");
//                for (Column column : columnList) {
//                    if (column.getIsKey()) {
//                        //暂时只支持单一主键
//                        sql.append(column.getName() + "=" + column.getValue());
//                        break;
//                    }
//                }
//                SQL_QUEUE.add(sql.toString());
//            }
//        } catch (InvalidProtocolBufferException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 保存插入语句
//     *
//     * @param entry
//     */
//    private void saveInsertSql(Entry entry) {
//        try {
//            RowChange rowChange = RowChange.parseFrom(entry.getStoreValue());
//            List<RowData> rowDatasList = rowChange.getRowDatasList();
//            for (RowData rowData : rowDatasList) {
//                List<Column> columnList = rowData.getAfterColumnsList();
//                StringBuffer sql = new StringBuffer("insert into " + entry.getHeader().getTableName() + " (");
//                for (int i = 0; i < columnList.size(); i++) {
//                    sql.append(columnList.get(i).getName());
//                    if (i != columnList.size() - 1) {
//                        sql.append(",");
//                    }
//                }
//                sql.append(") VALUES (");
//                for (int i = 0; i < columnList.size(); i++) {
//                    sql.append("'" + columnList.get(i).getValue() + "'");
//                    if (i != columnList.size() - 1) {
//                        sql.append(",");
//                    }
//                }
//                sql.append(")");
//                SQL_QUEUE.add(sql.toString());
//            }
//        } catch (InvalidProtocolBufferException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 入库
//     * @param sql
//     */
//    public void execute(String sql) {
//        Connection con = null;
//        try {
//            if(null == sql) return;
//            con = dataSource.getConnection();
//            QueryRunner qr = new QueryRunner();
//            int row = qr.execute(con, sql);
//            System.out.println("update: "+ row);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            DbUtils.closeQuietly(con);
//        }
//    }
//
//}