package com.example.oopforhits.data.implementations;

import com.example.oopforhits.data.model.Bet;
import com.example.oopforhits.data.model.Match;
import com.example.oopforhits.data.model.enums.BetStatus;
import com.example.oopforhits.data.model.enums.EndOfMatchType;
import com.example.oopforhits.data.model.enums.MatchStatus;
import com.example.oopforhits.domain.interfaces.BetManagerInterface;
import com.example.oopforhits.domain.repositories.BetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Ivan Vinnichenko
 */
@Component
@RequiredArgsConstructor
public class BetManager implements BetManagerInterface {

    private final BetRepository betRepository;

    @Override
    public void matchEnded(Match match, EndOfMatchType endOfMatchType) {
        for (Bet bet : betRepository.findAllByMatch(match)) {
            if (endOfMatchType == bet.getBetType()) {
                bet.setBetStatus(BetStatus.WIN);
            } else {
                bet.setBetStatus(BetStatus.LOSE);
            }
        }
    }

    @Override
    public void matchStarted(Match match) {
        for (Bet bet : betRepository.findAllByMatch(match)) {
            bet.setBetStatus(BetStatus.PLAYED);
        }
    }

    public boolean betIsAccept(Bet bet) {
        if (bet.getBetStatus() == BetStatus.NOT_STATE) {
            return false;
        }

        if (bet.getValue() <= 0) {
            return false;
        }

        if (bet.getRatio() > 1) {
            return false;
        }

        if (bet.getMatch().getMatchStatus() == MatchStatus.ENDED) {
            return false;
        }

        return bet.getId() > 0;
    }

}
