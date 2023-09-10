package ru.javawebinar.topjava.util;

import org.springframework.util.ClassUtils;

public class Profiles {

    public static final String PROD = "Prod", TEST = "Test", JPA = "jpa", DATAJPA = "datajpa", JDBC = "jdbc";

   // public static final String REPOSITORY_IMPLEMENTATION = JPA;
   // public static final String REPOSITORY_IMPLEMENTATION = JDBC;
    public static final String REPOSITORY_IMPLEMENTATION = DATAJPA;

    public static String getActiveDbProfile()
    {
        if (ClassUtils.isPresent("org.postgresql.Driver", null)) {
            return PROD;
        } else if (ClassUtils.isPresent("org.hsqldb.jdbcDriver", null)) {
            return TEST;
        } else {
            throw new IllegalStateException("Could not find DB driver");
        }
     }
}
