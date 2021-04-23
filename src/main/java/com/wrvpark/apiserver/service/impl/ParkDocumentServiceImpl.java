package com.wrvpark.apiserver.service.impl;

import com.wrvpark.apiserver.domain.NonParkDocumentLog;
import com.wrvpark.apiserver.domain.ParkDocument;
import com.wrvpark.apiserver.domain.Subcategory;
import com.wrvpark.apiserver.domain.User;
import com.wrvpark.apiserver.dto.ParkDocumentDTO;
import com.wrvpark.apiserver.dto.SearchDTO;
import com.wrvpark.apiserver.dto.requests.DeleteDTO;
import com.wrvpark.apiserver.repository.NonParkDocumentLogRepository;
import com.wrvpark.apiserver.repository.ParkDocumentRepository;
import com.wrvpark.apiserver.repository.SubCategoryRepository;
import com.wrvpark.apiserver.repository.UserRepository;
import com.wrvpark.apiserver.repository.search.LogicalOperations;
import com.wrvpark.apiserver.repository.search.ParkDocumentSpecification;
import com.wrvpark.apiserver.repository.search.SearchCriteria;
import com.wrvpark.apiserver.repository.search.SearchOperation;
import com.wrvpark.apiserver.service.ParkDocumentService;
import com.wrvpark.apiserver.util.ConstantUtil;
import com.wrvpark.apiserver.util.ResultEntity;
import com.wrvpark.apiserver.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * @author Isabel Ke
 * @author Vahid Haghighat
 * Original date:2020-02-18
 *
 * Description:park document service class that handles all the park document logic
 */
@Service
public class ParkDocumentServiceImpl implements ParkDocumentService {


    @Autowired
    private ParkDocumentRepository parkDocumentRepository;
    @Autowired
    private SubCategoryRepository  subCategoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NonParkDocumentLogRepository nonParkDocumentLogRepository;


    /**
     * get all the park documents order by create time in descending order
     * @return a list of customized park document or null if no park documents
     */
    @Override
    public ResultEntity<List<ParkDocumentDTO>> getAllParkDocuments(String message) {
        //order park document by create time in descending order
        List<ParkDocument> list=parkDocumentRepository.findAllByStatusOrderByCreateTimeDesc(ConstantUtil.STATUS_CREATED);
        if(list.isEmpty() || list==null)
        {
            return ResultEntity.successWithOutData("No park documents");
        }

        List<ParkDocumentDTO> parkDocumentDTOS = new ArrayList<>();
        for (ParkDocument document : list)
            parkDocumentDTOS.add(new ParkDocumentDTO(document));

        return ResultEntity.successWithData(parkDocumentDTOS,message);
    }

    /**
     * search park documents by the sub-category
     * @param subId id of the sub-category
     * @return matched park documents or an error message
     */
    @Override
    public ResultEntity<List<ParkDocumentDTO>> getParkDocumentsBySubCategory(String subId) {
        //1:valid the subName
        Subcategory subcategory;
        try {
             subcategory = subCategoryRepository.findById(subId).get();
        }catch (Exception e)
        {
            return ResultEntity.failed("This sub-category does not exist!");
        }

        List<ParkDocument> parkDocuments=parkDocumentRepository.findAllByStatusAndSubCategoryOrderByCreateTimeDesc(ConstantUtil.STATUS_CREATED,new Subcategory(subId));
        //check if any matches
        if(parkDocuments.size()==0 || parkDocuments==null)
        {
            return ResultEntity.successWithOutData("No park documents matches");
        }

        List<ParkDocumentDTO> parkDocumentDTOS = new ArrayList<>();
        for (ParkDocument document : parkDocuments)
            parkDocumentDTOS.add(new ParkDocumentDTO(document));
        return ResultEntity.successWithData(parkDocumentDTOS,"Found matched park documents");
    }

    /**
     * add a new park document
     * @param document that will be added
     * @return a customized list of park documents if the operation is successful
     */
    @Override
    public ResultEntity<List<ParkDocumentDTO>> addParkDocument(ParkDocument document) {
        if (subCategoryRepository.findById(document.getSubCategory().getId()).isEmpty())
            return ResultEntity.failed("There is no such subcategory!");

        document = parkDocumentRepository.save(document);
        document.setCreator(userRepository.findById(document.getCreator().getId()).get());
        document.setSubCategory(subCategoryRepository.findById(document.getSubCategory().getId()).get());

        NonParkDocumentLog log = new NonParkDocumentLog(document,
                                                      ConstantUtil.AUDIT_LOG_CREATED,
                                                      "Add a new park document",
                                                      "Add a new park document");
        nonParkDocumentLogRepository.save(log);
        return getAllParkDocuments("Added a new park document");
    }


    /**
     * update a park document
     * @param document the park document will be updated
     * @return a customized list of park documents if the operation is successful
     */
    @Override
    public ResultEntity<List<ParkDocumentDTO>> updateParkDocument(ParkDocument document, String modifierId) {
        if (parkDocumentRepository.findById(document.getId()).isEmpty())
            return ResultEntity.failed("There is no such document!");

        if (subCategoryRepository.findById(document.getSubCategory().getId()).isEmpty())
            return ResultEntity.failed("There is no such subcategory!");

        ParkDocument dbDocument = parkDocumentRepository.findById(document.getId()).get();
        dbDocument.setTitle(document.getTitle());
        dbDocument.setSubCategory(subCategoryRepository.findById(document.getSubCategory().getId()).get());
        dbDocument.setDescription(document.getDescription());
        dbDocument.setFileName(document.getFileName());
        dbDocument.setUpdateTime(new Date());

        User modifier = userRepository.findById(modifierId).get();

        dbDocument = parkDocumentRepository.save(dbDocument);

        NonParkDocumentLog log = new NonParkDocumentLog(dbDocument,
                                                      modifier,
                                                      ConstantUtil.AUDIT_LOG_UPDATED,
                                            "Update a park document",
                                               "Update a park document");
        nonParkDocumentLogRepository.save(log);
        return getAllParkDocuments("Updated a park document");
    }


    /**
     * delete a park document by its id
     * @param dto detailed information for deleting this park document
     * @return a customized list of park documents after the successful deleting,
     * or an error message if the operation fails
     */
    @Override
    public ResultEntity<List<ParkDocumentDTO>> deleteParkDocumentById(DeleteDTO dto) {
        try
        {
            ParkDocument document;
            try {
                 document = parkDocumentRepository.findById(dto.getItemId()).get();
            }
            catch (NoSuchElementException e){
                return ResultEntity.failed("This park document does not exist");
            }
            //update the status
            document.setStatus(ConstantUtil.STATUS_DELETED);
            //set the delete time
            Date deleteTime= new Date();
            document.setDeleteTime(deleteTime);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(deleteTime);
            calendar.add(Calendar.DATE, 90);
            Date expiryTime= calendar.getTime();
            document.setExpiryTime(expiryTime);
            //update the document to be DELETED
            parkDocumentRepository.save(document);

            //add an entry to the audit log
            NonParkDocumentLog log=new NonParkDocumentLog();
            log.setItemId(document.getId());
            log.setAction(ConstantUtil.AUDIT_LOG_DELETED);
            log.setCreateTime(new Date());
            log.setDescription(dto.getDescription());
            log.setReason(dto.getReason());
            User modifier=userRepository.findById(dto.getModifierId()).get();
            log.setModifier(modifier);
           log.setCategory(document.getSubCategory().getCategory());
          nonParkDocumentLogRepository.save(log);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResultEntity.failed("Failed to delete this park document!");
        }
        //return the updated list of park documents
        return getAllParkDocuments("Deleted a park document");
    }


    /**
     * search park documents by some title or description
     * @param parameters value of the title or description
     * @return a customized list of park documents that matches,
     *      * or an error message if no matches
     */
    @Override
    public ResultEntity<List<ParkDocumentDTO>> searchParkDocument(SearchDTO parameters) {
        ParkDocumentSpecification<String> defaultSpecification = new ParkDocumentSpecification<>(LogicalOperations.AND);
        defaultSpecification.add(new SearchCriteria<>("status", "CREATED", SearchOperation.EQUAL));
        ParkDocumentSpecification<String> textSpecification = new ParkDocumentSpecification<>(LogicalOperations.OR);
        if (!parameters.getName().isEmpty()) {
            textSpecification.add(new SearchCriteria<>("title", parameters.getName(), SearchOperation.MATCH));
            textSpecification.add(new SearchCriteria<>("description", parameters.getName(), SearchOperation.MATCH));
        }

        ParkDocumentSpecification<Subcategory> subcategorySpecification = new ParkDocumentSpecification<>(LogicalOperations.AND);
        if (!parameters.getSubId().isEmpty()) subcategorySpecification.add(new SearchCriteria<>("subCategory", new Subcategory(parameters.getSubId()), SearchOperation.EQUAL));

        ParkDocumentSpecification<Date> dateSpecification = new ParkDocumentSpecification<>(LogicalOperations.AND);
        if (!parameters.getStartTime().isEmpty()) dateSpecification.add(new SearchCriteria<>("createTime", TimeUtil.convertStringToDate(parameters.getStartTime() + " 00:00:00"), SearchOperation.GREATER_THAN_EQUAL));
        if (!parameters.getEndTime().isEmpty()) dateSpecification.add(new SearchCriteria<>("createTime", TimeUtil.convertStringToDate(parameters.getEndTime() + " 23:59:59"), SearchOperation.LESS_THAN_EQUAL));

        Specification<ParkDocument> specification = defaultSpecification;
        if (!textSpecification.isEmpty()) specification = specification.and(textSpecification);
        if (!subcategorySpecification.isEmpty()) specification = specification.and(subcategorySpecification);
        if (!dateSpecification.isEmpty()) specification = specification.and(dateSpecification);

        List<ParkDocument> documents = parkDocumentRepository.findAll(specification, Sort.by(Sort.Direction.DESC, "createTime"));

        if (documents.size() == 0)
        {
            return ResultEntity.successWithOutData("No park documents matches");
        } else {
            List<ParkDocumentDTO> parkDocumentDTOS = new ArrayList<>();
            for (ParkDocument document : documents)
                parkDocumentDTOS.add(new ParkDocumentDTO(document));
            return ResultEntity.successWithData(parkDocumentDTOS,"Found matched park documents");
        }
    }
}
