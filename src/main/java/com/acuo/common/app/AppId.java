package com.acuo.common.app;

import com.acuo.common.type.TypedString;
import com.acuo.common.util.ArgChecker;
import org.joda.convert.FromString;

public class AppId extends TypedString<AppId> {

	private static final long serialVersionUID = -7440919971063378153L;

	private AppId(String name) {
		super(name);
	}

	@FromString
	public static AppId of(String name) {
		ArgChecker.notNull(name, "name");
		return new AppId(name);
	}
}
