package com.yedam.appletree;

public class App 
{
    public static void main( String[] args )
    {
    	System.out.println("\n\t\t[게임설명]\n");
    	System.out.println("1. 농장에 사과나무를 최대 5개 심을 수 있다.");
    	System.out.println("2. 각 나무는 물주기(3회), 영양제주기(1회), 가지치기(2회) 수행완료 후 사과를 랜덤 수확할 수 있다.");
    	System.out.println("3. 각 지정된 수행 횟수를 넘을 경우에는 나무가 죽는다.");
    	System.out.println("4. 어떠한 수행을 할 때마다 hp가 10씩 감소한다.");
    	System.out.println("5. 온천을 이용하거나 상점에서 아이템을 구입 후 hp를 충전 시킬 수 있다.");
    	System.out.println("");
   
        MainMenu menu = new MainMenu();
        menu.run();
    }
    
}
