package com.epam.esm.repository;

import com.epam.esm.model.entity.UserOrder;
import com.epam.esm.model.entity.UserWithMaxTotalCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<UserOrder, Integer> {

    List<UserOrder> findByUserId(int id);

    @Query("""
           select
                new UserWithMaxTotalCost(u.userId as userId,
                sum(u.cost) as totalCost)
            from UserOrder u
            group by u.userId
            """)
    List<UserWithMaxTotalCost> findUsersWithTotalCost();

    @Query("""
               select
                    cwt.tagId
                    
               from
                    CertificateWithTag cwt,
                    (
                        select u.CertificateWithTagId AS id
                        from UserOrder u
                        where u.userId = 3
                    ) uo
                    where cwt.id = uo.id   
                    
            """)
    List<Object> findMostlyUsedTag(Integer userId);
}
