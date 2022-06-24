package com.alan.lab.server.utility.collectionmanagers;

public enum ResultOfSqlCollectionManager {
    ADD_SUCCESS,
    PASSPORT_ID_CONTAINS,
    PERSON_NOT_MIN,

    IS_EMPTY_TRUE,
    IS_EMPTY_FALSE,
    IS_EMPTY_ERROR,

    UPDATE_SUCCESS,
    UPDATE_NOT_OWNER,
    UPDATE_ERROR,

    REMOVE_SUCCESS,
    REMOVE_NOT_OWNER,
    REMOVE_ERROR,

    CLEAR_SUCCESS,
    CLEAR_ERROR_ROLLBACK,
    CLEAR_ERROR,


}
