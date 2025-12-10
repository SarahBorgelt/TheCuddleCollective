package com.techelevator.dao;

import com.techelevator.model.Parent;

public interface ParentDao {

    Parent addNewParent(Parent parent);
    Parent getParentById(long parentId);
}
