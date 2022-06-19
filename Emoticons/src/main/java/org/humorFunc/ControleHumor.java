package org.humorFunc;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class ControleHumor {
    public static void main( String[] args ) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("loja");
        EntityManager em = factory.createEntityManager();
        Scanner teclado = new Scanner(System.in);
        String nome = "";
        boolean nomeInvalido = false;
        while(!nome.equals("mestre andre")) {
            Humor humor = new Humor();
            System.out.println("Qual o seu nome?");
            do{
                nome = teclado.nextLine();
                nome = nome.toLowerCase();
                nome = StringUtils.capitalize(nome);
                nomeInvalido = !nomeValido(nome);
                if(nomeInvalido){
                    System.out.println("Nome invalido, digite novamente");
                }
            }while(nomeInvalido);
            humor.setFuncionario(nome);
            System.out.println("O que voce achou da sua ultima situação vivida");
            String resp = teclado.nextLine();
            int humorAtual = contEmote(resp);
            if (humorAtual == 0) {
                //neutro
                humor.setHumor("Neutro");
            } else if (humorAtual > 0) {
                //divertido
                humor.setHumor("Divertido");
            } else {
                //chateado
                humor.setHumor("Chateado");
            }
            em.getTransaction().begin();
            em.persist(humor);
            em.getTransaction().commit();
        }
        em.close();
    }

    private static boolean nomeValido(String nome) {
        boolean resp = nome.matches("[A-Z][a-z]+");
        return resp;
    }

    private static int contEmote(String resp) {
        int contFeliz = 0, contTriste = 0;
        String feliz = ":-)";
        String triste = ":-(";
        contFeliz = StringUtils.countMatches(resp,feliz);
        contTriste = StringUtils.countMatches(resp,triste);
        int humorA = contFeliz - contTriste;
        return humorA;
    }
}
