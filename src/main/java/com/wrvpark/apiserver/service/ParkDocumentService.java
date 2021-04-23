package com.wrvpark.apiserver.service;


import com.wrvpark.apiserver.domain.ParkDocument;
import com.wrvpark.apiserver.dto.ParkDocumentDTO;
import com.wrvpark.apiserver.dto.SearchDTO;
import com.wrvpark.apiserver.dto.requests.DeleteDTO;
import com.wrvpark.apiserver.util.ResultEntity;
import java.util.List;

/**
 * @author Isabel Ke
 * Original date:2020-02-18
 *
 * Description:park document service interface
 */
public interface ParkDocumentService {

    //get all park documents
    ResultEntity<List<ParkDocumentDTO>> getAllParkDocuments(String message);
    //get park documents by the sub-category
    ResultEntity<List<ParkDocumentDTO>> getParkDocumentsBySubCategory(String subId);
    //add a new park document
    ResultEntity<List<ParkDocumentDTO>> addParkDocument(ParkDocument document);
    //update a  park document
    ResultEntity<List<ParkDocumentDTO>> updateParkDocument(ParkDocument document, String modifierId);
    // delete a park document
    ResultEntity<List<ParkDocumentDTO>> deleteParkDocumentById(DeleteDTO id);
    //search a  park document
    ResultEntity<List<ParkDocumentDTO>> searchParkDocument(SearchDTO searchParam);
}
