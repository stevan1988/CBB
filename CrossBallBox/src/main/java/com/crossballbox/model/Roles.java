package com.crossballbox.model;

public enum Roles {

	USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

    private final String text;

    /**
     * @param text
     */
    private Roles(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
