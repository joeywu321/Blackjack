package com.evatar.blackjack.GameModels;

import com.evatar.blackjack.GameModels.Models.Card;
import com.evatar.blackjack.GameModels.Models.GameStatus;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

/**
 * Created by evatar on 12/27/16.
 */
@AutoValue
public abstract class PlayerState {
    public static Builder builder() {
        return new AutoValue_PlayerState.Builder();
    }

    public abstract long bet();

    public abstract ImmutableList<Card> cards();

    public abstract GameStatus status();

    @AutoValue.Builder
    abstract public static class Builder {
        abstract public Builder setBet(long value);

        abstract public Builder setCards(ImmutableList<Card> value);

        abstract public Builder setStatus(GameStatus value);

        abstract public PlayerState build();
    }
}
