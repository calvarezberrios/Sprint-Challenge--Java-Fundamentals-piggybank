package com.lambdaschool.piggybank.controllers;

import com.lambdaschool.piggybank.models.Coin;
import com.lambdaschool.piggybank.repositories.CoinRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.ArrayList;

@RestController
public class CoinController
{
    @Autowired
    CoinRepository coinrepo;

    DecimalFormat df = new DecimalFormat("$#,##0.00");

    @GetMapping(value = "/total", produces = {"application/json"})
    public ResponseEntity<?> listContents()
    {
        ArrayList<Coin> myList = new ArrayList<>();
        coinrepo.findAll().iterator().forEachRemaining(myList::add);

        double total = 0.0;

        for (Coin c : myList)
        {
            String coinname;

            if(c.getQuantity() == 1)
            {
                coinname = c.getName();
            }
            else
            {
                coinname = c.getNameplural();
            }

            System.out.println(c.getQuantity() + " " + coinname);

            total += c.getValue() * c.getQuantity();
        }
        System.out.println("The piggy bank holds " + df.format(total));

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
