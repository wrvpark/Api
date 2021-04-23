package com.wrvpark.apiserver.controllers;


import com.wrvpark.apiserver.domain.Item;
import com.wrvpark.apiserver.dto.ItemDTO;
import com.wrvpark.apiserver.dto.SearchDTO;
import com.wrvpark.apiserver.dto.requests.DeleteDTO;
import com.wrvpark.apiserver.dto.requests.NewItemRequest;
import com.wrvpark.apiserver.service.ItemService;
import com.wrvpark.apiserver.util.ConstantUtil;
import com.wrvpark.apiserver.util.ResultEntity;
import com.wrvpark.apiserver.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

/**
 * @author Chen Zhao
 * @author Vahid Haghighat
 * @author Isabel Ke
 * Original date:2020-02-12
 *
 * Description:Item(lost and found) controller
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/lostfound")
public class ItemController {
    @Autowired
    private ItemService itemService;

    /**
     * get all items
     * @return a customized json data type
     */
    @GetMapping(value = "")
    public ResultEntity<List<ItemDTO>> getAllItems()
    {
        return itemService.getAllItems(ConstantUtil.TYPE_LOST_FOUND);
    }

    /**
     * get all lost items
     * @return a customized json data type
     */
    @GetMapping(value = "/lost")
    public ResultEntity<List<ItemDTO>> getAllLost()
    {
        return itemService.getItemsBySubCategory(ConstantUtil.TYPE_LOST_FOUND,"Lost");
    }

    /**
     * get all found items
     * @return a customized json data type
     */
    @GetMapping(value = "/found")
    public ResultEntity<List<ItemDTO>> getAllFound()
    {
        return itemService.getItemsBySubCategory(ConstantUtil.TYPE_LOST_FOUND,"Found");
    }

    /**
     * add a new item
     * @param item that will be added
     * @return a list of items if the operation is successful.
     */
    @PostMapping(value = "")
    public ResultEntity<String> addItem(@RequestBody NewItemRequest item, Principal principal)  {
        return itemService.addItem(new Item(item, ConstantUtil.TYPE_LOST_FOUND, principal.getName()));
    }

    /**
     * delete an item by its id
     * @param
     * @return a list of items if the operation is successful.
     */
    @PutMapping("/delete")
    public ResultEntity<List<ItemDTO>> deleteItemById(@RequestBody DeleteDTO dto, Principal principal,
                                                      Authentication authentication)
    {
        dto.setModifierId(principal.getName());
        boolean isAdmin= SecurityUtil.hasRole(authentication, "admin");
        return itemService.deleteItemById(ConstantUtil.TYPE_LOST_FOUND,dto,isAdmin);
    }

    /**
     * get a item by its ID
     * @param id of the item that will be searched
     * @return an item if the operation is successful.
     */
    @GetMapping(value = "/{id}")
    public ResultEntity<ItemDTO> getItemByItemId(@PathVariable String id)
    {
        return itemService.getItemByItemId(id);
    }

    @PostMapping(value = "/{id}")
    public ResultEntity<String> updateItem(@PathVariable String id, @RequestBody NewItemRequest item, Principal principal) {
        return itemService.updateItem(new Item(id, item, ConstantUtil.TYPE_LOST_FOUND, principal.getName()));
    }


    /**
     * search items by certain certain criteria
     * @param name of the items will include
     * @param subId sub-id of the item's sub-category
     * @param startTime items posted after this time
     * @param endTime items posted before this time
     * @param uId user id of the items
     * @return a list of items that meet the conditions, otherwise a customized message
     */
    @GetMapping("/search")
    public ResultEntity<List<ItemDTO>> searchLostAndFoundItems(@RequestParam String name,
                                                                   @RequestParam String subId,
                                                                   @RequestParam String startTime,
                                                                   @RequestParam  String endTime,
                                                                   @RequestParam String uId)
    {
        SearchDTO parameters=new SearchDTO(name,subId,startTime,endTime,uId);
        return itemService.searchItems(parameters,ConstantUtil.TYPE_LOST_FOUND);
    }
}
