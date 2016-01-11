package com.csit.util;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.dialect.SQLServerDialect;
/**
 * @Description:编写自己的Dialect主要用来解决SqlServer 数据库的nvarchar类型的数据
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-17
 * @Author lys
 */
public class MySQLServerDialect extends SQLServerDialect {

	public MySQLServerDialect() {
		super();
		registerHibernateType(Types.NVARCHAR, Hibernate.STRING.getName());
		registerHibernateType(Types.DECIMAL, Hibernate.BIG_INTEGER.getName());     
		registerHibernateType(Types.LONGVARBINARY, Hibernate.BLOB.getName());
		registerHibernateType(Types.REAL, Hibernate.FLOAT.getName());
		registerHibernateType(Types.DECIMAL, Hibernate.BIG_DECIMAL.getName());
		registerHibernateType(Types.LONGVARCHAR, Hibernate.TEXT.getName());
	}
}
