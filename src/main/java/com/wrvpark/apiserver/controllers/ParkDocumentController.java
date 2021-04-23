package com.wrvpark.apiserver.controllers;

import com.wrvpark.apiserver.domain.ParkDocument;
import com.wrvpark.apiserver.dto.requests.CreateParkDocumentDTO;
import com.wrvpark.apiserver.dto.requests.DeleteDTO;
import com.wrvpark.apiserver.dto.ParkDocumentDTO;
import com.wrvpark.apiserver.dto.SearchDTO;
import com.wrvpark.apiserver.dto.requests.UpdateParkDocumentDTO;
import com.wrvpark.apiserver.service.ParkDocumentService;
import com.wrvpark.apiserver.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

/**
 * @author Isabel Ke
 * @author Vahid Haghighat
 * Original date:2020-02-18
 *
 * Description: park document controller that used as the entry to receive client-side requests
 * and respond properly to the requests
 */

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/documents")
public class ParkDocumentController {


    @Autowired
    private ParkDocumentService parkDocumentService;

    /**
     * get all park documents
     * @return a customized list of park documents, or an error message if no documents
     */
    @GetMapping("")
    public ResultEntity<List<ParkDocumentDTO>> getAllParkDocuments()
    {
        return parkDocumentService.getAllParkDocuments("Get all park documents");
    }

    /**
     * add a new park document
     * @param doc that will be added
     * @return a customized list of park documents if the operation is success,
     * or an error message if fails
     */
    @PostMapping("")
    public ResultEntity<List<ParkDocumentDTO>> addParkDocument(@RequestBody CreateParkDocumentDTO doc, Principal principal)
    {
        return parkDocumentService.addParkDocument(new ParkDocument(doc, principal.getName()));
    }

    /**
     * update a park document
     * @param doc that will be updated
     * @return a customized list of park documents if the operation is success,
     *      * or an error message if fails
     */
    @PutMapping("/{id}")
    public ResultEntity<List<ParkDocumentDTO>> updateParkDocument(@RequestBody UpdateParkDocumentDTO doc, @PathVariable String id, Principal principal)
    {
        return parkDocumentService.updateParkDocument(new ParkDocument(doc, id), principal.getName());
    }

    /**
     * Delete a park document
     * @param
     * @return a customized list of park documents if the operation is success,
     *      * or an error message if fails
     */
    @PutMapping("/delete")
    public ResultEntity<List<ParkDocumentDTO>> deleteParkDocumentById(@RequestBody DeleteDTO dto,Principal principal)
    {
        //get the user id from the access token and set it for the dto"
        dto.setModifierId(principal.getName());
        return parkDocumentService.deleteParkDocumentById(dto);
    }

    /**
     * Search park documents by subId
     * @param subId id of the sub-category
     * @return a customized list of park documents if the operation is success,
     *      or an error message if fails
     */
    @GetMapping("/{subId}")
    public ResultEntity<List<ParkDocumentDTO>> getParkDocumentsBySubCategory(@PathVariable String subId)
    {
        return parkDocumentService.getParkDocumentsBySubCategory(subId);
    }


    /**
     * Searches for a specific park document.
     * @param name The title of the park document.
     * @param subId The subcategory id of the park document.
     * @param startTime The start of the period of the search.
     * @param endTime The end of the period of the search.
     * @param uId The user id of the post creator.
     * @return A list of all found park documents.
     */
    @GetMapping("/search")
    public ResultEntity<List<ParkDocumentDTO>> searchParkDocuments(@RequestParam String name,
                                                                   @RequestParam String subId,
                                                                   @RequestParam String startTime,
                                                                   @RequestParam  String endTime,
                                                                   @RequestParam String uId)
    {
        SearchDTO parameters=new SearchDTO(name,subId,startTime,endTime,uId);
        return parkDocumentService.searchParkDocument(parameters);
    }
}
