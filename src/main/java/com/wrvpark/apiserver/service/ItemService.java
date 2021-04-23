package com.wrvpark.apiserver.service;

import com.wrvpark.apiserver.domain.Item;
import com.wrvpark.apiserver.dto.requests.DeleteDTO;
import com.wrvpark.apiserver.dto.ItemDTO;
import com.wrvpark.apiserver.dto.SearchDTO;
import com.wrvpark.apiserver.util.ResultEntity;

import java.util.List;
/**
 * @author Chen Zhao
 * Original date:2020-02-12
 *
 * Description:item service interface
 */
public interface ItemService {
    /*
    get all items
     */
    ResultEntity<List<ItemDTO>> getAllItems(String itemType);


    /*
    add a new item
     */
    ResultEntity<String> addItem(Item item);

    /*
       delete an item by its id
    */
    ResultEntity<List<ItemDTO>> deleteItemById(String itemType, DeleteDTO dto, boolean isAdmin);

    /*
    update an item by its id
     */
    ResultEntity<String> updateItem(Item item);

    /*
    get an item by its id
     */
    ResultEntity<ItemDTO> getItemByItemId(String itemId);

    /*
    get items for a specific sub-category
     */
    ResultEntity<List<ItemDTO>> getItemsBySubCategory(String itemType, String subCategory);

    /*
    search items by certain criteria
     */
    ResultEntity<List<ItemDTO>> searchItems(SearchDTO parameters, String itemType);

}
