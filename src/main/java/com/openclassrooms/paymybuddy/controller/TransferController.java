package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.dto.TransactionConnectionDescriptionAmountDTO;
import com.openclassrooms.paymybuddy.dto.UserTransferDTO;
import com.openclassrooms.paymybuddy.dto.ContactsAndTransactionsListDTO;

import com.openclassrooms.paymybuddy.service.CustomService;
import com.openclassrooms.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Controller class handling user transfers.
 */
@Controller
public class TransferController {

    @Autowired
    CustomService customService;

    @Autowired
    UserService userService;

    /**
     * Retrieves the transfer details and renders the transfer page.
     *
     * @param model Model object to be used to add attributes to the view.
     * @return String representing the name of the view to be rendered.
     */
/*    @RequestMapping("/home/transfer")
    public String getUser(Model model) {

        ContactsAndTransactionsListDTO transferDetails = customService.getTransferDetails();

        model.addAttribute("transfer", transferDetails);
        model.addAttribute("transaction", new UserTransferDTO());
        return "transfer";
    }*/

    /**
     * Handles the user's money transfer to another user.
     *
     * @param transaction UserTransferDTO object containing the transfer details.
     * @return String representing the name of the view to be rendered.
     */
    @PostMapping("/home/transfer")
    public String transfer(@ModelAttribute("transaction") UserTransferDTO transaction){

        boolean moneySent = customService.sendMoneyToUser(transaction);

        if(moneySent) {
            return "redirect:/home/transfer?success";
        } else {
            return "redirect:/home/transfer?error";
        }
    }

    @RequestMapping("/home/transfer")
    public String listBooks(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {

        ContactsAndTransactionsListDTO transferDetails = customService.getTransferDetails();

        model.addAttribute("transfer", transferDetails);
        model.addAttribute("transaction", new UserTransferDTO());

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(3);

        Page<TransactionConnectionDescriptionAmountDTO> transferList = customService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("transferList", transferList);

        int totalPages = transferList.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "transfer";
    }
}
