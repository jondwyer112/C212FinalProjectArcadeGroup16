package edu.iu.c212.places.games.blackjack;

public class BlackjackDealer extends BlackjackParticipant {

    private int shownCard;
    private int dealerBest;
    private boolean firstCard;
    public BlackjackDealer() {
        hit();
        hit();
        firstCard = true;
    }
    @Override public void hit() {
        int addNum = nums.get(rand.nextInt(nums.size()));
        nums.remove(addNum);
        if ((handTotals[0] + addNum) > 21) {
            bust = true;
        }
        else {
            if (addNum == 1) {
                isAOne = true;
                handTotals[0] += 1;
                handTotals[1] += 11;
            } else {
                handTotals[0] += addNum;
                handTotals[1] += addNum;
            }
        }
        if (firstCard = true) {
            shownCard = addNum;
            firstCard = false;
        }
        System.out.println(handTotals[0]);

    }


    public String getPartialHand () {


         return shownCard + " + ???";

     }
     public void play() {
        while (getBestTotal() <= 17) {
            hit();
        }
         if (bust) {
             handTotals[0] = -1;


         }

    }
    public boolean getBust() {
        return bust;
    }
}
