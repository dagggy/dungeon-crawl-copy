package com.example.dungeaoncrawler.fight;

import com.example.dungeaoncrawler.logic.actors.Actor;
import com.example.dungeaoncrawler.logic.actors.Player;
import com.example.dungeaoncrawler.logic.actors.Skeleton;
import com.example.dungeaoncrawler.logic.items.Cards;
import com.example.dungeaoncrawler.logic.status.Heal;
import com.example.dungeaoncrawler.logic.status.Poisone;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Fight {
    public static void main(String[] args) {
        Player player = new Player(10,0,0,4);
        Skeleton skeleton = new Skeleton(20, 3,2);
        player.endFight();
            System.out.println(player.getPlayingDeck().size());
        for (int i = 0; i < 4; i++) {
//        while (skeleton.getHealth() > 0){
            playermove(player, skeleton);
        }
    }
    private static void playermove(Actor player, Actor opponent){
        ArrayList<Cards> hand = drawRandomCards((Player) player);
        int roll = rollDice(((Player) player).getDice());
        printHand(hand);
        if (canPlayCard(roll, hand)) {
            int cardNumber = getUserInput();
            playCard(player, opponent, cardNumber, hand, roll);
        }
    }

    private static void playCard(Actor player, Actor opponent, int cardIndex, ArrayList<Cards> hand, int roll){
            if (hand.get(cardIndex).getCardCost()>=roll){
                resolveCardEffect(player, opponent, hand.get(cardIndex));
            } else {
                System.out.println("You dont have point action to play this card");
            }
    }

    private static void resolveCardEffect(Actor player, Actor opponent, Cards card){
        switch (card.getCardsType()){
            case DECREASE_ARMOR -> opponent.setArmor(Math.max(opponent.getArmor() - card.getValue(), 0));
            case RESISTANCE -> player.setResistance(card.getValue());
            case DISPELL -> player.setDispell(card.getValue());
            case POISON -> opponent.setPoisone(new Poisone(player.getPower(), card.getValue()));
            case ATTACK -> opponent.takeDamage(card.getValue());
            case SPELL -> opponent.takeMagicDamage(card.getValue());
            case ARMOR -> player.setArmor(card.getValue());
            case HEAL -> player.setHeal(new Heal(player.getPower(), card.getValue()));
            case STUN -> opponent.setStun(player.getPower());
        }
    }

    private static boolean canPlayCard(int roll, ArrayList<Cards> hand){
        if (hand.size()<=0) return false;
        else return roll >= getLowestCost(hand);
    }


    private static int getLowestCost(ArrayList<Cards> hand){
        int lowestCost = 100;
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getCardCost()< lowestCost) lowestCost = hand.get(i).getCardCost();
        }
        return lowestCost;
    }

    private static ArrayList<Cards> drawRandomCards(Player player){
        Random random = new Random();
        int cardsOnHand = player.getCards();
        ArrayList<Cards> hand = new ArrayList<>();
        ArrayList<Cards> deck = player.getPlayingDeck();
        for (int i = 0; i < cardsOnHand; i++) {
            if (deck.size() > player.getCards()-hand.size()){
                int index =random.nextInt(deck.size());
                hand.add(deck.get(index));
                deck.remove(index);
            }
            else {
                deck = new ArrayList<>(player.getDeck());
                int index = random.nextInt(deck.size());
                hand.add(deck.get(index));
                deck.remove(index);
            }
        }
        System.out.println("hand size " + hand.size());
        System.out.println("deck size " + deck.size());
        return hand;
    }

    private static int rollDice(int dices){
        Random random = new Random();
        int diceSum = 0;
        for (int i = 0; i < dices; i++) {
            int score = random.nextInt(6)+1;
            diceSum += score;
            System.out.println((i+1) + ". Dice roll = " + score);
        }
        System.out.println("--------------------");
        System.out.println("Throws value: " + diceSum);
        System.out.println();
        return diceSum;
    }

    private static void printHand(ArrayList<Cards> hand){
        System.out.println();
        System.out.println("PLAYER HAND");
        System.out.println();
        for (int i = 0; i < hand.size(); i++) {
            System.out.println("CARD NUMBER" + (i+1));
//            System.out.println("Card name: " + hand.get(i).getName());
            System.out.println("Card Cost: " + hand.get(i).getCardCost());
            System.out.println("Rarity: " + hand.get(i).getRarity());
            System.out.println("Description: " + hand.get(i).getDescription());
            System.out.println();
        }
    }

    private static int getUserInput(){
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        return number;
    }
}
