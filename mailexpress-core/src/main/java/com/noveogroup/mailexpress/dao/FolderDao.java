package com.noveogroup.mailexpress.dao;

import com.noveogroup.mailexpress.domain.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Maxim Baev
 */
@Repository("folderDao")
public interface FolderDao extends JpaRepository<Folder, Long> {
}
