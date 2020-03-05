package com.agora.paygatedatastreamer.repository;


import com.agora.paygatedatastreamer.entity.PaymentEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentEventRepository extends CrudRepository<PaymentEvent,Integer> {

}
