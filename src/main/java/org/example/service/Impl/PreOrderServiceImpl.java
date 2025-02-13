package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.FlowerSize;
import org.example.entity.Preorder;
import org.example.entity.enums.Condition;
import org.example.entity.enums.Precondition;
import org.example.entity.enums.Status;
import org.example.repository.PreOrderRepository;
import org.example.repository.PurposeRepository;
import org.example.service.IPreOrderService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PreOrderServiceImpl implements IPreOrderService {
    private final PreOrderRepository preOrderRepository;


    @Override
    public List<Preorder> findPreorderByAccount(int Account) {
        return preOrderRepository.findPreordersByAccount_AccountIDAndStatusOrderByDateDesc(Account, Status.ENABLE);
    }

    @Override
    public Preorder findPreorderByPreorderID(int id) {
        return preOrderRepository.findPreorderByIdAndStatus(id, Status.ENABLE);
    }

    @Override
    public Preorder findPreorderByID(int id) {
        return preOrderRepository.findPreorderById(id);
    }

    @Override
    public List<Preorder> findPreorderWatingOrdering() {
        List<Precondition> preconditions = new ArrayList<>();
        preconditions.add(Precondition.Waiting);
        preconditions.add(Precondition.Ordering);
        return preOrderRepository.findPreordersByPreconditionInAndStatus(preconditions,Status.ENABLE);
    }


}
