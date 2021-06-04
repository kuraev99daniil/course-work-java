package sample.view.table_view;

import javafx.scene.AccessibleAttribute;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollBar;

public class TableViewSettings {
    public static <T extends Control> void removeScrollBar(T table) {
        ScrollBar scrollBar = (ScrollBar) table.queryAccessibleAttribute(AccessibleAttribute.VERTICAL_SCROLLBAR);
        /*
         *This null-check is for safety reasons if you are using when the table skin isn't yet initialized.
         * If you use this method in a custom skin you wrote, where you @Override the layoutChildren method,
         * use it there, and it should be always initialized, so null-check would be unnecessary.
         *
         */
        if (scrollBar != null) {
            scrollBar.setPrefHeight(0);
            scrollBar.setMaxHeight(0);
            scrollBar.setOpacity(1);
            scrollBar.setVisible(false); // If you want to keep the scrolling functionality then delete this row.
        }
    }
}
