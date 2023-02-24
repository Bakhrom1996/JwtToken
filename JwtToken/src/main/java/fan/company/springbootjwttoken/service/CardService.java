package fan.company.springbootjwttoken.service;

import fan.company.springbootjwttoken.entity.Card;
import fan.company.springbootjwttoken.entity.Income;
import fan.company.springbootjwttoken.entity.Outcome;
import fan.company.springbootjwttoken.payload.ApiResult;
import fan.company.springbootjwttoken.payload.TransferDto;
import fan.company.springbootjwttoken.repository.CardRepository;
import fan.company.springbootjwttoken.repository.IncomeRepository;
import fan.company.springbootjwttoken.repository.OutcomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    @Autowired
    CardRepository cardRepository;
    @Autowired
    OutcomeRepository outcomeRepository;
    @Autowired
    IncomeRepository incomeRepository;


    public ApiResult doTransfer(String username, TransferDto transferDto) {

        boolean existsByUsernameAndNumber = cardRepository.existsByUsernameAndNumber(username, transferDto.getFromCardId());

        if (!existsByUsernameAndNumber)
            return new ApiResult(false, "Karta foydalanuvchiga tegishli emas!");

        boolean existsByNumber = cardRepository.existsByNumber(transferDto.getToCardId());
        if (!existsByNumber)
            return new ApiResult(false, "Karta mavjud emas!");

        Card fromCard = cardRepository.findByNumber(transferDto.getFromCardId());
        Card toCard = cardRepository.findByNumber(transferDto.getToCardId());


        if (!fromCard.isActive())
            return new ApiResult(false, "Kartangiz faol emas!");

        if (!toCard.isActive())
            return new ApiResult(false, "Karta faol emas!");


        if (0 > (fromCard.getBalance() - transferDto.getSummaToTransfer() * 1.01))
            return new ApiResult(false, "Kartada yetarlicha mablag' mavjud emas! Joriy ballance " + fromCard.getBalance());

        try {
            fromCard.setBalance(fromCard.getBalance() - transferDto.getSummaToTransfer() * 1.01);
            toCard.setBalance(toCard.getBalance() + transferDto.getSummaToTransfer());


            Card savefromCard = cardRepository.save(fromCard);
            Card savetoCard = cardRepository.save(toCard);

            System.out.println("savefromCard " + savefromCard);
            System.out.println("savetoCard " + savetoCard);

            Outcome outcome = new Outcome();
            outcome.setFromCardId(fromCard);
            outcome.setToCardId(toCard);
            outcome.setAmount(transferDto.getSummaToTransfer());
            outcome.setCommissionAmount(transferDto.getSummaToTransfer() * 0.01);
            outcome.setDate(new Date());
            Outcome outcomeSaved = outcomeRepository.save(outcome);
            System.out.println("outcomeSaved " + outcomeSaved);


            Income income = new Income();
            income.setFromCardId(fromCard);
            income.setToCardId(toCard);
            income.setAmount(transferDto.getSummaToTransfer());
            income.setDate(new Date());
            Income incomeSaved = incomeRepository.save(income);
            System.out.println("incomeSaved " + incomeSaved);

            return new ApiResult(true, "Transfer muvoffaqiyatli amalga oshirildi!");

        } catch (Exception e) {

            return new ApiResult(false, "Xatolik yuz berdi!");
        }

    }

    public ApiResult addCard(Card card) {

        boolean existsByNumber = cardRepository.existsByNumber(card.getNumber());

        if (existsByNumber)
            return new ApiResult(false, "Karta mavjud!");

        cardRepository.save(card);

        return new ApiResult(true, "Muvoffaqiyatli saqlandi!");

    }

    public List<Outcome> getCardOutcome(Long cardId, String username) {

        Card card = cardRepository.findByNumber(cardId);
        if (card == null) return null;

        boolean existsByUsernameAndNumber = cardRepository.existsByUsernameAndNumber(username, card.getNumber());

        if (!existsByUsernameAndNumber) return null;

        List<Outcome> allByFromCardId = outcomeRepository.findAllByFromCardId(card);

        return allByFromCardId;
    }

    public List<Income> getCardIncome(Long cardId, String username) {

        Card card = cardRepository.findByNumber(cardId);
        if (card == null) return null;

        boolean existsByUsernameAndNumber = cardRepository.existsByUsernameAndNumber(username, card.getNumber());

        if (!existsByUsernameAndNumber) return null;

        List<Income> allByFromCardId = incomeRepository.findAllByToCardId (card);

        return allByFromCardId;
    }


}
