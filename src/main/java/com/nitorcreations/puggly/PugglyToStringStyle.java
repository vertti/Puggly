package com.nitorcreations.puggly;

import org.apache.commons.lang3.builder.ToStringStyle;

public class PugglyToStringStyle extends ToStringStyle {

    private static final long serialVersionUID = 1L;

    public static final ToStringStyle PUGGLY_STYLE = new PugglyToStringStyle();

    /**
     * <p>Use the static constant rather than instantiating.</p>
     */
    PugglyToStringStyle() {
        super();
        this.setUseClassName(false);
        this.setUseIdentityHashCode(false);
        this.setFieldSeparator(", ");
    }

    /**
     * <p>Ensure <code>Singleton</code> after serialization.</p>
     *
     * @return the singleton
     */
    private Object readResolve() {
        return PUGGLY_STYLE;
    }

}
