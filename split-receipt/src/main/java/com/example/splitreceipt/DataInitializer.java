package com.example.splitreceipt;

import com.example.splitreceipt.domain.Input;
import com.example.splitreceipt.domain.Receipt;
import com.example.splitreceipt.domain.User;
import com.example.splitreceipt.repository.InputRepository;
import com.example.splitreceipt.repository.ReceiptRepository;
import com.example.splitreceipt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Behzod on 09, March, 2021
 */
@Component
public class DataInitializer implements ApplicationRunner {

    private final InputRepository inputRepository;
    private final UserRepository userRepository;
    private final ReceiptRepository receiptRepository;

    @Autowired
    public DataInitializer (
            InputRepository inputRepository,
            UserRepository userRepository,
            ReceiptRepository receiptRepository
    ) {
        this.inputRepository = inputRepository;
        this.userRepository = userRepository;
        this.receiptRepository = receiptRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createUsers();
        createReceipt();
        createInputs();
    }

    private void createUsers() {
        List<User> users = new ArrayList<>();

        User alan = new User();
        alan.setId(1);
        alan.setName("Alan");
        users.add(alan);

        User ben = new User();
        ben.setId(2);
        ben.setName("Ben");
        users.add(ben);

        User chris = new User();
        chris.setId(3);
        chris.setName("Chris");
        users.add(chris);

        User dave = new User();
        dave.setId(4);
        dave.setName("Dave");
        users.add(dave);

        userRepository.saveAll(users);
    }

    private void createReceipt() {
        List<Receipt> receipts = new ArrayList<>();

        Receipt mountainsReceipt = new Receipt();
        mountainsReceipt.setComment("Road to mountains");
        receipts.add(mountainsReceipt);

        Receipt giftsReceipt = new Receipt();
        giftsReceipt.setComment("Gifts for march 8");
        receipts.add(giftsReceipt);

        receiptRepository.saveAll(receipts);
    }

    private void createInputs() {
        List<Input> inputs = new ArrayList<>();

        Input alan1 = new Input();
        alan1.setId(1);
        alan1.setComment("food");
        alan1.setAmount(1.0);
        alan1.setReceipt(new Receipt(1));
        alan1.setUser(new User(1));
        inputs.add(alan1);

        Input ben1 = new Input();
        ben1.setId(2);
        ben1.setComment("food ben");
        ben1.setAmount(2.0);
        ben1.setReceipt(new Receipt(1));
        ben1.setUser(new User(2));
        inputs.add(ben1);

        Input chris1 = new Input();
        chris1.setId(3);
        chris1.setComment("food chris");
        chris1.setAmount(3.0);
        chris1.setReceipt(new Receipt(1));
        chris1.setUser(new User(3));
        inputs.add(chris1);

        Input dave1 = new Input();
        dave1.setId(4);
        dave1.setComment("food dave1");
        dave1.setAmount(1.0);
        dave1.setReceipt(new Receipt(1));
        dave1.setUser(new User(4));
        inputs.add(dave1);

        Input dave2 = new Input();
        //dave2.setId(5);
        dave2.setComment("food dave2");
        dave2.setAmount(4.0);
        dave2.setReceipt(new Receipt(1));
        dave2.setUser(new User(4));
        inputs.add(dave2);



        Input alan1Receipt2 = new Input();
        //alan1Receipt2.setId(1);
        alan1Receipt2.setComment("restaurant");
        alan1Receipt2.setAmount(0.0);
        alan1Receipt2.setReceipt(new Receipt(2));
        alan1Receipt2.setUser(new User(1));
        inputs.add(alan1Receipt2);

        Input ben1Receipt2 = new Input();
        //alan1Receipt2.setId(1);
        ben1Receipt2.setComment("cake");
        ben1Receipt2.setAmount(50000.0);
        ben1Receipt2.setReceipt(new Receipt(2));
        ben1Receipt2.setUser(new User(2));
        inputs.add(ben1Receipt2);

        Input chris1Receipt2 = new Input();
        //alan1Receipt2.setId(1);
        chris1Receipt2.setComment("roses");
        chris1Receipt2.setAmount(1000000.0);
        chris1Receipt2.setReceipt(new Receipt(2));
        chris1Receipt2.setUser(new User(3));
        inputs.add(chris1Receipt2);

        inputRepository.saveAll(inputs);
    }
}
