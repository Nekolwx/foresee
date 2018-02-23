package com.foresee.ftcsp.order.service;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.stat.TableStat;
import com.alibaba.druid.util.JdbcConstants;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class SqlTest {
    String sql = "SELECT \n" +
            "\t\tg.GOODS_ID as goodsId,\n" +
            "\t\tcount(g.GOODS_ID),\n" +
            "\t\tg.NAME_short as name,\n" +
            "\t\tgc.NAME_JC as goodsCategoryName,\n" +
            "\t\tg.VERSION_NO as versionNo,\n" +
            "\t    (SELECT sum(pd.ORG_PRICE*pd.PRODUCT_NUM) from t_pdt_product_goods pd where pd.GOODS_ID = g.GOODS_ID ) as price,\n" +
            "        (SELECT sum(pd.DISCOUNT_PRICE*pd.PRODUCT_NUM) from t_pdt_product_goods pd where pd.GOODS_ID = g.GOODS_ID ) as discountPrice,\n" +
            "        (SELECT  pg.PRODUCT_NUM*pg.FREE_MONTHS + p.VALIDITY_DATE  from t_pdt_product_goods pg , t_pdt_product p  where pg.GOODS_ID = g.GOODS_ID and pg.PRODUCT_ID = p.ID and pg.IS_MAIN_PRODUCT = 'Y'  ) as validityDate,\n" +
            "\t\tg.term_of_service as termOfService,\n" +
            "\t\tg.SALE_START_TIME as saleStartTime,\n" +
            "\t\tg.SALE_END_TIME as saleEndTime,\n" +
            "\t\tg.STATUS as status,\n" +
            "\t\tg.OPEN_PATTERN as openPattern\n" +
            "\t\tfrom \n" +
            "\t\tt_pdt_goods g\n" +
            "\t\tLEFT JOIN t_pdt_goods_area ga on ga.GOODS_ID=g.GOODS_ID\n" +
            "\t\tLEFT JOIN t_pdt_goods_category gc on gc.ID = g.GOODS_CATEGORY_ID\n" +
            "\t\twhere \n" +
            "\t\texists(select 1 from \n" +
            "\t\t(select (select goods_id from t_pdt_goods g1 \n" +
            "\t\twhere g1.code = g.code order by CONVERT (substring(version_no, 2) , DECIMAL) DESC LIMIT 0,1) as goods_id\n" +
            " \t\t\t\tfrom t_pdt_goods g\n" +
            " \t\t\t\tgroup by code ) t \n" +
            " \t\twhere g.GOODS_ID=t.goods_id)";

    String sql2 = "SELECT\n" +
            "\t\tg.GOODS_ID as goodsId,\n" +
            "\t\tg.NAME_short as nameShort,\n" +
            "\t\tg.IS_POPULAR as isPopular,\n" +
            "\t\tg.STATUS as status,\n" +
            "\t\tg.GOODS_CATEGORY_ID as goodsCategoryId,\n" +
            "\t\tgc.NAME_JC as  goodsCategoryNameJc,\n" +
            "\t\tpg.IS_MAIN_PRODUCT as isMainProduct,\n" +
            "\t\tcount(1)\n" +
            "\tFROM\n" +
            "\t\tt_pdt_product_goods pg,\n" +
            "\t\tt_pdt_goods g,\n" +
            "\t\tt_pdt_goods_category gc\n" +
            "\tWHERE\n" +
            "\t\tpg.GOODS_ID = g.GOODS_ID\n" +
            "\tAND g.GOODS_CATEGORY_ID=gc.ID\n" +
            "\tAND (g. STATUS != '4' and g. STATUS != '5')\n" +
            "\tAND pg.PRODUCT_ID = ?\n" +
            "\tgroup by g.GOODS_ID\n" +
            "\tORDER BY convert(g.NAME_short using gbk) ASC";
    @Test
    public void testSql() throws Exception {
        long start = System.currentTimeMillis();
        final String dbType = JdbcConstants.MYSQL; // 可以是ORACLE、POSTGRESQL、SQLSERVER、ODPS等
//        String sql = "select * from t";
        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql2, dbType);
//        System.out.println(stmtList);
        stmtList.forEach(sqlStatement -> {
            SchemaStatVisitor schemaStatVisitor = new SchemaStatVisitor();
            sqlStatement.accept(schemaStatVisitor);
            Map<TableStat.Name, TableStat> tables = schemaStatVisitor.getTables();
            System.out.println(tables.keySet());
        });
        long end = System.currentTimeMillis();
        System.out.println(end-start);
        start = System.currentTimeMillis();
//        String sql = "select * from t";
        stmtList = SQLUtils.parseStatements(sql, dbType);
//        System.out.println(stmtList);
        stmtList.forEach(sqlStatement -> {
            SchemaStatVisitor schemaStatVisitor = new SchemaStatVisitor();
            sqlStatement.accept(schemaStatVisitor);
            Map<TableStat.Name, TableStat> tables = schemaStatVisitor.getTables();
            System.out.println(tables.keySet());
        });
        end = System.currentTimeMillis();
        System.out.println(end-start);
    }
}
