package com.example.demo.controllers;

import com.example.demo.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class ClientController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.url}")
    String apiUrl;

    @GetMapping(value = {"/", "/clients"})
    public String getAll(Model model) {
        List clients = restTemplate.getForObject(apiUrl + "/clients/", List.class);
        model.addAttribute("clients", clients);
        model.addAttribute("client", new Client());
        return "index-client";
    }

    @PostMapping(value = "/add-client")
    public String addClient(Model model, @ModelAttribute("client") Client client) {
        restTemplate.postForObject(apiUrl+"/clients/", client, Client.class);
        return "redirect:/clients";
    }

    @GetMapping(value = "/delete-client/{id}")
    public String deleteClientById(Model model, @PathVariable long id) {
        restTemplate.delete(apiUrl+"/clients/"+id);
        return "redirect:/clients";
    }

    @GetMapping(value = "/show-client/{id}")
    public String show(Model model, @PathVariable long id) {
        Client client = restTemplate.getForObject(apiUrl+"/clients/"+id, Client.class);
        model.addAttribute("client",client);
        return "edit";
    }

    @PostMapping(value = "/save-client")
    public String save(Model model, @ModelAttribute("client") Client client) {
        restTemplate.put(apiUrl+"/clients/"+client.getId(), client, Client.class);
        return "redirect:/clients";
    }

}
