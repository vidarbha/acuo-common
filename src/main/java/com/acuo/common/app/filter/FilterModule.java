package com.acuo.common.app.filter;

import com.google.inject.servlet.ServletModule;
import com.thetransactioncompany.cors.CORSFilter;

import javax.inject.Singleton;

public class FilterModule extends ServletModule {

  @Override
  protected void configureServlets() {
    bind(CORSFilter.class).in(Singleton.class);

    filter("*").through(CORSFilter.class);
  }

}