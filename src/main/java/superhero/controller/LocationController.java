/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package superhero.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import superhero.dao.LocationDao;

/**
 *
 * @author ciruf
 */
@Controller
@RequestMapping("/locations")
public class LocationController {
    
    @Autowired
    LocationDao locationDao;
    
    @GetMapping
    public String getLocations() {
        return "HomeBase.html";
    }
}