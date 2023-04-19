package com.crud.CrudApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.core.status.Status;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class ProductController {
    
    @Autowired
    private ProductService service;

    //list all
    @GetMapping("/products")
    public List<Product> list(){
        return service.listAll();
    }
//show by id
    @GetMapping("products/{id}")
        public ResponseEntity<Product> get(@PathVariable Integer id){
            try{
           Product prooduct = service.get(id);
           return new ResponseEntity<Product>(prooduct, HttpStatus.OK);
        }catch(NoSuchElementException e){
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
    
    }
//add 
    @PostMapping("/products")
    public ResponseEntity add(@RequestBody Product product){
        service.save(product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
    
    //update
   @PutMapping("/products/{id}")
   public ResponseEntity<?> update(@RequestBody Product product,
   @PathVariable Integer id){
    try{
    Product exisProduct = service.get(id);
    service.save(product);
    return new ResponseEntity<Product>(product,HttpStatus.OK);
      
    } catch(NoSuchElementException e){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
   }

   //delete
   @DeleteMapping("/products/{id}")
   public ResponseEntity<?> delete(@RequestBody Product product ,@PathVariable Integer id){
    
    try{
        Product exisProduct = service.get(id);
        service.delete(id);
        return new ResponseEntity<>("successfully deleted",HttpStatus.OK);
    }catch(NoSuchElementException e){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
   }
}
