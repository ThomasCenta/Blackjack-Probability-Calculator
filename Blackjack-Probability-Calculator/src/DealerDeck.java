/**
 *
 * @author Thomas Centa
 *
 */
public class DealerDeck implements DealerDeckInterface {

  /**
   * total number of cards in the deck.
   */
  private int numCards;

  /**
   * index i corresponds to the number of cards with rank i + 1 in the deck.
   * aces are rank 1
   */
  private int[] cardsInDeck;

  /**
   * Default constructor. Makes an empty deck
   */
  public DealerDeck() {
    this.numCards = 0;
    this.cardsInDeck = new int[10];
  }

  /**
   * This creates a DealerDeck from a regular 13 rank deck.
   *
   * @param regularDeck
   *          This is a regular 13 rank deck to make the dealers deck from
   */
  public DealerDeck(Deck regularDeck) {
    assert regularDeck != null : "regularDeck is null";
    this.numCards = 0;
    this.cardsInDeck = new int[10];
    for (int i = 0; i < 13; i++) {
      this.numCards += regularDeck.numCard(i);
      if (i > 9) {
        this.cardsInDeck[9] += regularDeck.numCard(i);
      } else {
        this.cardsInDeck[i] += regularDeck.numCard(i);
      }
    }
  }

  @Override
  public double drawProbability(int rank, int cardsTakenOut, int numRank) {
    double toReturn = 0.0;
    if (this.cardsInDeck[rank] > numRank && this.numCards >= cardsTakenOut) {
      toReturn = (this.cardsInDeck[rank] - numRank) * 1.0 / (this.numCards - cardsTakenOut);
    }
    return toReturn;
  }

  @Override
  public void addCard(int rank) {
    this.cardsInDeck[rank]++;
    this.numCards++;
  }

  @Override
  public void removeCard(int rank) {
    assert this.cardsInDeck[rank] > 0 : "Deck does not contain that card";
    this.cardsInDeck[rank]--;
    this.numCards--;

  }

  @Override
  public int numCardRank(int rank) {
    assert rank < 10 && rank >= 0 : "rank " + rank + " is not a valid rank";
    return this.cardsInDeck[rank];
  }

  @Override
  public int numCardsInDeck() {
    return this.numCards;
  }

  @Override
  public void takeOutHand(VariableRankHand hand) {
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < hand.numCardRank10(i); j++) {
        this.removeCard(i); // let the kernel method take care of it.
      }
    }
  }

  @Override
  public void addHand(VariableRankHand hand) {
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < hand.numCardRank10(i); j++) {
        this.addCard(i); // let the kernel method take care of it.
      }
    }
  }

  @Override
  public String toString() {
    String toReturn = "";
    for (int i = 0; i < 9; i++) {
      toReturn += this.cardsInDeck[i] + " ";
    }
    toReturn += this.cardsInDeck[9];
    return toReturn;
  }

}
