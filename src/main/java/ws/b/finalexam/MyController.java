/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.b.finalexam;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author Nama: Dwi Aprilya Anggoro Putry || NIM: 20200140055
 */
@RestController
@CrossOrigin
@RequestMapping("/data")
public class MyController {

    Person pn = new Person();
    PersonJpaController pjc = new PersonJpaController();

    @GetMapping("{id}")
    public List<Person> getNameById(@PathVariable("id") int id) {
        List<Person> dummy = new ArrayList<>();
        try {
            pn = pjc.findPerson(id);
            dummy.add(pn);
        } catch (Exception e) {
        }
        return dummy;
    }

    @GetMapping
    public List<Person> getAll() {
        List<Person> dummy = new ArrayList<>();
        try {
            dummy = pjc.findPersonEntities();
        } catch (Exception e) {
        }
        return dummy;
    }
    
    @PostMapping
    public String createData(HttpEntity<String> paket) {
        String message = "";

        try {
            String json_receive = paket.getBody();
            ObjectMapper map = new ObjectMapper();

            Person data = new Person();

            data = map.readValue(json_receive, Person.class);
            pjc.create(data);
            message = data.getNama() + " Data Saved";

        } catch (Exception e) {
            message = "Failed";
        }

        return message;
    }

    @PutMapping()
    public String editData(HttpEntity<String> kiriman) {
        String message = "no action";

        try {
            String json_receive = kiriman.getBody();
            ObjectMapper map = new ObjectMapper();

            Person newDatas = new Person();

            newDatas = map.readValue(json_receive, Person.class);
            pjc.edit(newDatas);
            message = newDatas.getNama() + " Has Been Updated";

        } catch (Exception e) {
            message = "Failed";
        }
        return message;
    }

    @DeleteMapping()
    public String deleteData(HttpEntity<String> kiriman) {
        String message = "no action";

        try {
            String json_receive = kiriman.getBody();
            ObjectMapper map = new ObjectMapper();

            Person newDatas = new Person();

            newDatas = map.readValue(json_receive, Person.class);
            pjc.destroy(newDatas.getId());
            message = newDatas.getNama() + " Has Been Deleted";

        } catch (Exception e) {
            message = "Failed";
        }
        return message;
    }

}
