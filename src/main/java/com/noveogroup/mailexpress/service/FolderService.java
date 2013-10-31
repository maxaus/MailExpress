package com.noveogroup.mailexpress.service;

import com.noveogroup.mailexpress.model.Folder;

import java.util.List;

/**
 * @author Maxim Baev
 */
public interface FolderService {

    List<Folder> findAll();
}
