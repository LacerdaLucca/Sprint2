package Loja;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Oferta {
    private int id;
    private String nome, descricao, data;
    private double preco,desconto;
    public String getNome() {
        return this.nome;
    }

    public Oferta(){
        Scanner teclado = new Scanner(System.in);
        System.out.println("Voce deseja cadastrar o nome?");
        String resp = teclado.nextLine();
        if (resp.equals("sim") || resp.equals("s")) {
            System.out.println("Qual é o nome?");
            this.nome = teclado.nextLine();
        } else {
            System.out.println("OK, vamos para a proxima.");
        }
        System.out.println("Voce deseja cadastrar a descricao?");
        resp = teclado.nextLine();
        if (resp.equals("sim") || resp.equals("s")) {
            System.out.println("Qual é a descricao?");
            this.descricao = teclado.nextLine();
        } else {
            System.out.println("OK, vamos para a proxima.");
        }
        System.out.println("Voce deseja cadastrar o desconto?");
        resp = teclado.nextLine();
        if (resp.equals("sim") || resp.equals("s")) {
            System.out.println("Qual é o desconto?");
            this.desconto = teclado.nextDouble();
            teclado.nextLine();
        } else {
            System.out.println("OK, vamos para a proxima.");
        }
        System.out.println("Voce deseja cadastrar o preco?");
        resp = teclado.nextLine();
        if (resp.equals("sim") || resp.equals("s")) {
            System.out.println("Qual é o preco?");
            this.preco = teclado.nextDouble();
            teclado.nextLine();
        } else {
            System.out.println("OK, vamos para a proxima.");
        }
        System.out.println("Voce deseja cadastrar a data?");
        resp = teclado.nextLine();
        if (resp.equals("sim") || resp.equals("s")) {
            System.out.println("Qual é a data?");
            this.data = teclado.nextLine();
        } else {
            System.out.println("OK!");
        }if(this.nome==null && this.descricao==null && this.desconto==0 && this.preco == 0 && this.data==null){
            System.out.println();
            throw new vazioException();
        }
    }
    public Oferta(int id, String nome, String descricao, double desconto, double preco, Date data){
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.desconto = desconto;
        this.preco = preco;
        if(data != null)
            this.data = setData(data);
    }

    private String setData(Date data) {
//        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
//        try {
            return data.toString();
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
    }

    public String getDescricao() {
        return this.descricao;
    }

    public java.sql.Date getData() {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        java.sql.Date data = null;
        try {
            if(this.data != null)
                data = new java.sql.Date(formato.parse(this.data).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    public double getPreco() {
        return this.preco;
    }

    public double getDesconto() {
        return this.desconto;
    }

    public void setID(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Oferta [" +
                "id=" + this.id +
                ", nome='" + this.nome + '\'' +
                ", descricao='" + this.descricao + '\'' +
                ", preco=" + this.preco +
                ", desconto=" + this.desconto +
                ", data=" + this.data +
                ']';
    }

    public int getId() {
        return this.id;
    }

    public void altera(Scanner teclado) {
        System.out.println("Voce deseja atualizar o nome?");
        String resp = teclado.nextLine();
        if(resp.equals("sim") || resp.equals("s")){
            System.out.println("Qual é o novo nome? O atual é" + this.nome);
            this.nome = teclado.nextLine();
        }else{
            System.out.println("OK, vamos para a proxima.");
        }
        System.out.println("Voce deseja atualizar a descricao?");
        resp = teclado.nextLine();
        if(resp.equals("sim") || resp.equals("s")){
            System.out.println("Qual é a nova descricao? A atual é" + this.descricao);
            this.descricao = teclado.nextLine();
        }else{
            System.out.println("OK, vamos para a proxima.");
        }
        System.out.println("Voce deseja atualizar o desconto?");
        resp = teclado.nextLine();
        if(resp.equals("sim") || resp.equals("s")){
            System.out.println("Qual é o novo desconto? O atual é " + this.desconto);
            this.desconto = teclado.nextDouble();
            teclado.nextLine();
        }else{
            System.out.println("OK, vamos para a proxima.");
        }
        System.out.println("Voce deseja atualizar o preco?");
        resp = teclado.nextLine();
        if(resp.equals("sim") || resp.equals("s")){
            System.out.println("Qual é o novo preco? O atual é " + this.preco);
            this.preco = teclado.nextDouble();
            teclado.nextLine();
        }else{
            System.out.println("OK, vamos para a proxima.");
        }
        System.out.println("Voce deseja atualizar a data?");
        resp = teclado.nextLine();
        if(resp.equals("sim") || resp.equals("s")){
            System.out.println("Qual é a nova data? A atual é" + this.data);
            this.data = teclado.nextLine();
        }else{
            System.out.println("Atualização feita");
        }
    }
}
