package com.acuo.common.model.ids;

import com.acuo.common.type.TypedString;
import com.acuo.common.util.ArgChecker;
import org.joda.convert.FromString;

public class BookId extends TypedString<BookId> {

    private BookId(String name) {
        super(name);
    }

    @FromString
    public static BookId fromString(String id) {
        ArgChecker.notNull(id, "id");
        return new BookId(id);
    }

}
