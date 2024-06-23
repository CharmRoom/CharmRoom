package com.charmroom.charmroom.service;

import com.charmroom.charmroom.entity.Market;
import com.charmroom.charmroom.entity.User;
import com.charmroom.charmroom.entity.Wish;
import com.charmroom.charmroom.exception.BusinessLogicError;
import com.charmroom.charmroom.exception.BusinessLogicException;
import com.charmroom.charmroom.repository.MarketRepository;
import com.charmroom.charmroom.repository.UserRepository;
import com.charmroom.charmroom.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishService {
    private final WishRepository wishRepository;
    private final UserRepository userRepository;
    private final MarketRepository marketRepository;

    public Wish create(String username, Integer marketId) {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new BusinessLogicException(BusinessLogicError.NOTFOUND_USER));
        Market market = marketRepository.findById(marketId).orElseThrow(() ->
                new BusinessLogicException(BusinessLogicError.NOTFOUND_ARTICLE));

        Optional<Wish> found = wishRepository.findByUserAndMarket(user, market);

        if (found.isPresent()) {
            Wish wish = found.get();
            wishRepository.delete(wish);
            return null; // 찜하기 취소
        } else {
            return wishRepository.save(
                    Wish.builder()
                            .user(user)
                            .market(market)
                            .build()
            );
        }
    }
}