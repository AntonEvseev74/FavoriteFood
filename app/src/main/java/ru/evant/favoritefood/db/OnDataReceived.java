package ru.evant.favoritefood.db;

import java.util.List;

import ru.evant.favoritefood.adapter.ListItem;

public interface OnDataReceived {
    void onReceived(List<ListItem> list);
}
