package com.wrvpark.apiserver.service.impl;

import com.wrvpark.apiserver.domain.Item;
import com.wrvpark.apiserver.domain.NonParkDocumentLog;
import com.wrvpark.apiserver.domain.Subcategory;
import com.wrvpark.apiserver.domain.User;
import com.wrvpark.apiserver.dto.ItemDTO;
import com.wrvpark.apiserver.dto.SearchDTO;
import com.wrvpark.apiserver.dto.requests.DeleteDTO;
import com.wrvpark.apiserver.repository.*;
import com.wrvpark.apiserver.repository.search.ItemSpecification;
import com.wrvpark.apiserver.repository.search.LogicalOperations;
import com.wrvpark.apiserver.repository.search.SearchCriteria;
import com.wrvpark.apiserver.repository.search.SearchOperation;
import com.wrvpark.apiserver.service.ItemService;
import com.wrvpark.apiserver.util.ResultEntity;
import com.wrvpark.apiserver.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Chen Zhao
 * @author Vahid Haghighat
 * @author Isabel Ke
 * Original date:2020-02-12
 *
 * Description:item service class that handles all the item logic
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private NonParkDocumentLogRepository nonParkDocumentLogRepository;

    /**
     * get all lost & found items
     * @param itemType item type for the items
     * @return a list of items for a specific item type, or an message when no data
     */
    @Override
    public ResultEntity<List<ItemDTO>> getAllItems(String itemType){
        List<ItemDTO> items = getItemsWithCriteria(itemType);
        if(items.size() == 0)
            return ResultEntity.successWithOutData("Nothing available!");
        return  ResultEntity.successWithData(items,"Get all active items.");
    }


    /**
     * Add a new item based on the specific item type
     * @param item new item that will be added
     * @return a list of items for a specific item type, or an error message when no data found
     */
    @Override
    public ResultEntity<String> addItem(Item item) {
        Item savedItem = itemRepository.save(item);
        return  ResultEntity.successWithData(savedItem.getId(), "Added an item!");
    }

    /**
     * Delete an existing item
     * @param itemType the item type
     * @return a list of items for a specific item type, or an error message if the operation fails
     */
    @Override
    public ResultEntity<List<ItemDTO>> deleteItemById(String itemType, DeleteDTO dto, boolean isAdmin) {

        if(dto.getItemId().isEmpty())
        {
            return ResultEntity.failed("Invalid item id");
        }


            Item item=itemRepository.findById(dto.getItemId()).get();
            //check if the id matches with the item type
            if(!item.getItemType().equals(itemType))
            {
                return ResultEntity.failed("Invalid item id");
            }
            //check if it is the creator or admin
            if(!dto.getModifierId().equals(item.getCreator().getId()) && !isAdmin)
            {
                return ResultEntity.failed("Only the creator or admin can delete it");
            }

            itemRepository.deleteById(dto.getItemId());
        //record it
        if(isAdmin)
        {
            User modifier=userRepository.findById(dto.getModifierId()).get();
            NonParkDocumentLog log=new NonParkDocumentLog(modifier,dto.getReason(),dto.getDescription(),
                    "DELETE",new Date(),dto.getItemId(),
                    item.getSubcategory().getCategory()
            );
            nonParkDocumentLogRepository.save(log);
        }
        return  ResultEntity.successWithOutData("Deleted an item!");
    }

    /**
     * Update an existing item
     * @param item the updated item
     * @return a list of items for a specific item type, or an error message if the operation fails
     */
    @Override
    public ResultEntity<String> updateItem(Item item){
        if (itemRepository.findById(item.getId()).isPresent()) {
            Item dbItem = itemRepository.findById(item.getId()).get();
            if (!dbItem.getCreator().getId().equals(item.getCreator().getId()))
                return ResultEntity.failed("You do not have permission to change this item.");
            dbItem = itemRepository.save(item);
            return ResultEntity.successWithData(dbItem.getId(), "Updated!");
        }
        return ResultEntity.failed("Not found!");
    }

    /**
     * Get an item by id
     * @param itemId item id
     * @return a list of items for a specific item id, or an error message if no data matches
     */
    @Override
    public ResultEntity<ItemDTO> getItemByItemId(String itemId) {
        //check if the item id is valid
        if(itemId.isEmpty())
        {
            return ResultEntity.failed("Invalid item id");
        }

        Item item = itemRepository.findById(itemId).get();
        if(item == null ||!item.getStatus().equalsIgnoreCase("Active"))
        {
            return ResultEntity.failed("This Item does not exist");
        }
        ItemDTO itemDTO = new ItemDTO(item);
        return ResultEntity.successWithData(itemDTO,"Item found");
    }

    /**
     * Get items based on the item type and the sub-category
     * @param itemType item type
     * @param subCategory sub-category name
     * @return a list of items for a specific item type and sub-category, or an error message if the no data matches
     */
    @Override
    public ResultEntity<List<ItemDTO>> getItemsBySubCategory(String itemType,String subCategory) {
        List<ItemDTO> items = getItemsWithCriteria(itemType, subCategory);
        if(items.size() == 0)
            return ResultEntity.successWithOutData("Nothing available!");
        return  ResultEntity.successWithData(items,"Found matched items");
    }

    /**
     * Search items based on certain conditions
     * @param parameters search conditions
     * @param itemType item type
     * @return a list of items that meet these conditions, or an error message if no data matches
     */
    @Override
    public ResultEntity<List<ItemDTO>> searchItems(SearchDTO parameters, String itemType) {
        ItemSpecification<String> defaultSpecification = new ItemSpecification<>(LogicalOperations.AND);
        defaultSpecification.add(new SearchCriteria<>("status", "Active", SearchOperation.EQUAL));
        defaultSpecification.add(new SearchCriteria<>("itemType", itemType, SearchOperation.EQUAL));

        ItemSpecification<String> textSpecification = new ItemSpecification<>(LogicalOperations.OR);
        if (!parameters.getName().isEmpty()) {
            textSpecification.add(new SearchCriteria<>("title", parameters.getName(), SearchOperation.MATCH));
            textSpecification.add(new SearchCriteria<>("description", parameters.getName(), SearchOperation.MATCH));
        }

        ItemSpecification<Subcategory> subcategorySpecification = new ItemSpecification<>(LogicalOperations.AND);
        if (!parameters.getSubId().isEmpty()) subcategorySpecification.add(new SearchCriteria<>("subcategory", new Subcategory(parameters.getSubId()), SearchOperation.EQUAL));

        ItemSpecification<Date> dateSpecification = new ItemSpecification<>(LogicalOperations.AND);
        if (!parameters.getStartTime().isEmpty()) dateSpecification.add(new SearchCriteria<>("createTime", TimeUtil.convertStringToDate(parameters.getStartTime() + " 00:00:00"), SearchOperation.GREATER_THAN_EQUAL));
        if (!parameters.getEndTime().isEmpty()) dateSpecification.add(new SearchCriteria<>("createTime", TimeUtil.convertStringToDate(parameters.getEndTime() + " 23:59:59"), SearchOperation.LESS_THAN_EQUAL));

        Specification<Item> specification = defaultSpecification;
        if (!textSpecification.isEmpty()) specification = specification.and(textSpecification);
        if (!subcategorySpecification.isEmpty()) specification = specification.and(subcategorySpecification);
        if (!dateSpecification.isEmpty()) specification = specification.and(dateSpecification);

        List<Item> items = itemRepository.findAll(specification, Sort.by(Sort.Direction.DESC, "createTime"));

        if (items.size() == 0)
        {
            return ResultEntity.successWithOutData("No item matches");
        } else {
            List<ItemDTO> itemDTOS = new ArrayList<>();
            for(Item item : items)
                itemDTOS.add(new ItemDTO(item));
            return ResultEntity.successWithData(itemDTOS,"Found matched items");
        }
    }

    /**
     *get the items for a specific type
     * @param itemType of the items
     * @return a list of ItemDTO that meets the condition
     */
    private List<ItemDTO> getItemsWithCriteria(String itemType) {
        List<Subcategory> subcategories = categoryRepository.findByName(itemType).getSubcategoryList();
        List<Item> allItems = itemRepository.findAllByStatusAndSubcategoryIn("Active", subcategories);
        List<ItemDTO> items = new ArrayList<>();
        for (Item item : allItems)
                items.add(new ItemDTO(item));
        return items;
    }

    /**
     * Search items based on the item type and the sub-category
     * @param itemType item type
     * @param subCategory sub-category
     * @return the matched items
     */
    private List<ItemDTO> getItemsWithCriteria(String itemType, String subCategory) {
        Subcategory subcategory = subCategoryRepository.findByName(subCategory);

        List<Item> allItems= itemRepository.findAllByStatusAndSubcategory("Active", subcategory);
        List<ItemDTO> items = new ArrayList<>();
        for (Item item : allItems)
            if(item.getSubcategory().getName().equals(subCategory))
                items.add(new ItemDTO(item));
        return items;
    }
}