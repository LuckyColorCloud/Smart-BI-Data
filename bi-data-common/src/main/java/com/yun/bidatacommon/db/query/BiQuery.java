package com.yun.bidatacommon.db.query;

import java.lang.annotation.*;

/**
 * @author Sober
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface BiQuery {

    QueryType value() default QueryType.EQ;

}
