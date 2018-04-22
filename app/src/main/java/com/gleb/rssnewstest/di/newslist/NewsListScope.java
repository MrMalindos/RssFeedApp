package com.gleb.rssnewstest.di.newslist;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

@Retention(value = RetentionPolicy.RUNTIME)
@Scope
public @interface NewsListScope {
}
