package fan.company.springbootjwttoken.controller;

import fan.company.springbootjwttoken.entity.Card;
import fan.company.springbootjwttoken.entity.Income;
import fan.company.springbootjwttoken.entity.Outcome;
import fan.company.springbootjwttoken.payload.ApiResult;
import fan.company.springbootjwttoken.payload.TransferDto;
import fan.company.springbootjwttoken.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/card")
public class CardController {

    @Autowired
    CardService transferService;


    @PostMapping("/transfer")
    public HttpEntity<?> doTransfer(@RequestBody TransferDto transferDto){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        ApiResult apiResult = transferService.doTransfer(username, transferDto);
        return ResponseEntity.status(apiResult.isSuccess()? HttpStatus.OK:HttpStatus.CONFLICT).body(apiResult);
    }

    @PostMapping
    public HttpEntity<?> addCard(@Valid @RequestBody Card card){
        ApiResult apiResult = transferService.addCard(card);
        return ResponseEntity.status(apiResult.isSuccess()? HttpStatus.CREATED:HttpStatus.CONFLICT).body(apiResult);
    }

    @GetMapping("/outcome/{cardId}")
    public HttpEntity<?> outcomeCard(@PathVariable  Long cardId){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        List<Outcome> cardOutcome = transferService.getCardOutcome(cardId, username);
        return ResponseEntity.status(!cardOutcome.isEmpty()? HttpStatus.OK:HttpStatus.BAD_REQUEST).body(cardOutcome);
    }

    @GetMapping("/income/{cardId}")
    public HttpEntity<?> incomeCard(@PathVariable Long cardId){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        List<Income> cardIncome = transferService.getCardIncome(cardId, username);
        return ResponseEntity.status(!cardIncome.isEmpty() ? HttpStatus.OK:HttpStatus.BAD_REQUEST).body(cardIncome);
    }


}
