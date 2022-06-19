package Loja;

import Connections.ConnectionFactory;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        ConnectionFactory cf = new ConnectionFactory();
        boolean sair = false;
        System.out.println("Bem vindo ao sistema");
        do {
            try(Connection connection = cf.iniciaConexao()) {
                OfertaDAO ofertaDao = new OfertaDAO(connection);
                Scanner teclado = new Scanner(System.in);
                int opcao = 0;
                System.out.println("O que deseja fazer?");
                System.out.println("1 - Para INSERIR uma nova oferta");
                System.out.println("2- Para ATUALIZAR uma oferta");
                System.out.println("3 - Para EXCLUIR uma oferta");
                System.out.println("4- Para LISTAR as ofertas");
                System.out.println("5- Para SAIR do sistema");
                opcao = teclado.nextInt();
                teclado.nextLine();
                switch (opcao) {
                    case (1):
                        adiciona(ofertaDao);
                        break;
                    case (2):
                        atualiza(ofertaDao,teclado);
                        break;
                    case (3):
                        exclui(ofertaDao, teclado);
                        break;
                    case (4):
                        lista(ofertaDao,teclado);
                        break;
                    case (5):
                        sair = confirmaSaida(teclado);
                        break;
                    default:
                        throw new InputMismatchException();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (InputMismatchException ex) {
                System.out.println("Resposta invalida. Por favor, tente novamente, digite um numero entre 1 e 5");
            }
        } while (!sair);
    }

    private static void lista(OfertaDAO ofertaDao, Scanner teclado) {
        List<Oferta> ofertas = ofertaDao.lista();
        List<Oferta> escolhidas = new ArrayList<Oferta>();
        System.out.println("Qual produto voce deseja listar?");
        String nome = teclado.nextLine();
        String[] nomeSplit = nome.split(" ");
        ofertas.forEach(oferta -> {
            for (int i = 0; i < nomeSplit.length; i++) {
                if(oferta.getNome().contains(nomeSplit[i])){
                    escolhidas.add(oferta);
                }
            }
        });
        int quantOfertasVal = escolhidas.size();
        if(escolhidas.size() < 3){
            System.out.println("Nao existem pelo menos 3 opcoes com o nome \"" + nome + "\". Foram encontras " + quantOfertasVal + " ocorrencias");
            ofertas.removeIf(oferta -> escolhidas.contains(oferta));
            if(ofertas.size() > 0) {
                escolhidas.add(ofertas.get(0));
            }
            if(ofertas.size() > 1 && quantOfertasVal < 2) {
                escolhidas.add(ofertas.get(1));
            }
            if (ofertas.size() > 2 && quantOfertasVal == 0){
                escolhidas.add(ofertas.get(2));
            }
        }
            escolhidas.forEach(oferta -> System.out.println(oferta));
//        System.out.println(escolhidas.size());

    }

    private static void exclui(OfertaDAO ofertaDao, Scanner teclado) {
        System.out.println("Qual id será deletado?");
        int id = teclado.nextInt();
        teclado.nextLine();
        Oferta o = ofertaDao.busca(id);
        if (o != null) {
            ofertaDao.remove(id);
            System.out.println("ID "+ id + ", removido com sucesso");
        }else {
            System.out.println("ID nao encontrado");
        }
    }

    private static void atualiza(OfertaDAO ofertaDao, Scanner teclado) {
        System.out.println("Qual id será atualizado?");
        int id = teclado.nextInt();
        teclado.nextLine();
        try {
            Oferta o = ofertaDao.busca(id);
            if (o != null) {
                o.altera(teclado);
                ofertaDao.atualiza(o);
            } else {
                System.out.println("Id nao encontrado, vamos adicionar esse id");
                o = new Oferta();
                ofertaDao.adicionaID(o, id);
            }
        }catch (InputMismatchException ex){
            System.out.println("Ocorreu um problema reinicie por favor");
        }catch (vazioException ex){
            System.out.println("Voce nao digitou nenhum valor, cadastro rejeitado. Tente novamente.");
        }catch (RuntimeException ex){
            System.out.println("Ocorreu um problema na entrada da data reinicie por favor");
        }
    }

    private static void adiciona(OfertaDAO ofertaDao) {
        List<Oferta> ofertas = ofertaDao.lista();
        System.out.println("Adicionando oferta");
        try {
            if (ofertas.isEmpty()) {
                System.out.println("ATENCAO! Nao foram encontras ofertas cadastras, dessa forma será feito o cadastro de 3 ofertas");
                System.out.println("Na primeira oferta");
                Oferta oferta0 = new Oferta();
                ofertaDao.adiciona(oferta0);
                System.out.println("Na segunda oferta");
                Oferta oferta1 = new Oferta();
                ofertaDao.adiciona(oferta1);
                System.out.println("Na terceira oferta");
            }
            Oferta oferta = new Oferta();
            ofertaDao.adiciona(oferta);
        }catch (InputMismatchException ex){
            System.out.println("Ocorreu um problema reinicie por favor");
        }catch (vazioException ex){
            System.out.println("Voce nao digitou nenhum valor, cadastro rejeitado. Tente novamente.");
        }catch (RuntimeException ex){
            System.out.println("Ocorreu um problema na entrada da data reinicie por favor");
        }
    }

    private static boolean confirmaSaida(Scanner teclado) {
        System.out.println("Voce realmente deseja sair?");
        boolean sair = false;
        String resp = (teclado.nextLine()).toLowerCase();
        if(resp.equals("sim") || resp.equals("s")) {
            sair = true;
            System.out.println("Volte Sempre");
        }else{
            System.out.println("Entao continue utilizando o sistema");
        }
        return sair;
    }
}
