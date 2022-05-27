import java.io.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class Matching {


    public static void main(String args[]) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            try {
                String input = br.readLine();

                if (input.compareTo("QUIT") == 0)
                    break;

                command(input);

            } catch (IOException e) {

                System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());

            }
        }
    }

    static final int k = 6;

    private static void command(String input) {
        try {
            char inputCase = input.charAt(0);
            String inputData = input.substring(2);
            switch (inputCase) {
                case '<':
                    readData(inputData);
                    break;
                case '?':
                    searchData(inputData);
                    break;
                case '@':
                    printData(inputData);
                    break;
                default: // invalid case
                    return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("씨발?"); // 주석처리
        }

    }

    static HashMap<Integer, AVLTree<ExtendedStr>> hashTable = new HashMap<>(100);
    // 테이블에는 해쉬코드 값을 기준으로 넣음
    // 해쉬코드가 같은 문자열들은 사전순으로 AVLTree에 집어넣는다.
    // 완전히 같은 문자열의 경우 한 노드 안에 연결 리스트로 쑤셔넣는다. -> AVLNode, AVLTree 수정한다.

    private static void readData(String inputData) throws IOException {
        hashTable = new HashMap<>(100); // 초기화
        File file = new File(inputData); // 이걸로 바꿔야 함.
        //File file = new File("code/data.txt"); // 테스트용.
        //System.out.println(file); // 주석처리
        Scanner scanner = new Scanner(file);
        List<String> stringList = new LinkedList<>();
        while (scanner.hasNext()) {
            String input = scanner.nextLine();
            if (input.equals("")) break;

            stringList.add(input);
        }

        // 012345678

        for (int i = 0; i < stringList.size(); i++) {
            String line = stringList.get(i);
            for (int j = 0; j < line.length() - 5; j++) {
                ExtendedStr extendedStr = new ExtendedStr(line.substring(j, j + 6), new Pair(i + 1, j + 1));
                if (hashTable.get(extendedStr.hashCode()) == null) {
                    //System.out.println("진입");
                    hashTable.put(extendedStr.hashCode(), new AVLTree<ExtendedStr>());
                    // System.out.println(hashTable.get(extendedStr.hashCode()));
                }
                hashTable.get(extendedStr.hashCode()).insert(extendedStr);
            }
        }
    }

    private static void searchData(String inputData) {
        // inputData 가 6이 아니라 6보다 큰 경우에 대해서도 다뤄야함.
        // 6개 단위로 inputData를 짜르고, 마지막에 남는 나머지를
        List<AVLNode<ExtendedStr>> searchResultsList = new LinkedList<>();
        int quotient = inputData.length() / 6;
        int remain = inputData.length() % 6;
        int back = (6 - remain) % 6;
        int i;
        for (i = 0; i < inputData.length() - 5; i += 6) {
            ExtendedStr extendedStr = new ExtendedStr(inputData.substring(i, i + 6), new Pair(0, 0));
            if (hashTable.get(extendedStr.hashCode()) == null) { //부분배열이 없으면? 원본배열도 없다!
                System.out.println("(0, 0)");
                return;
            }
            AVLNode<ExtendedStr> searchResult = hashTable.get(extendedStr.hashCode()).search(extendedStr);
            if (searchResult.item == null) { //NIL CASE: NOT FOUND 부분배열이 없으면? 원본배열도 없다!
                System.out.println("(0, 0)");
                return;
            }
            searchResultsList.add(searchResult);
        }
        if (remain != 0) {
            ExtendedStr extendedStr = new ExtendedStr(inputData.substring(i - back), new Pair(0, 0));
            if (hashTable.get(extendedStr.hashCode()) == null) { //부분배열이 없으면? 원본배열도 없다!
                System.out.println("(0, 0)");
                return;
            }
            AVLNode<ExtendedStr> searchResult = hashTable.get(extendedStr.hashCode()).search(extendedStr);
            if (searchResult.item == null) { //NIL CASE: NOT FOUND 부분배열이 없으면? 원본배열도 없다!
                System.out.println("(0, 0)");
                return;
            }
            searchResultsList.add(searchResult);
        }

        AVLNode<ExtendedStr> tmpNode = searchResultsList.get(0); // 여기 도달했으면 무조건 존재한다.
        //System.out.println("back: " + back);
        StringBuilder tmp = new StringBuilder();
        while (tmpNode != null) {
            //System.out.println(tmpNode);
            /*if(searchAnswer(tmpNode, searchResultsList, 1, back)) {
                if(tmpNode.linkedNode == null) {
                    System.out.print(tmpNode.item.position);
                }
                else {
                    System.out.print(tmpNode.item.position + " ");
                }
            }*/
            if (searchAnswer(tmpNode, searchResultsList, 1, back)) {
                tmp.append(tmpNode.item.position + " ");
            }
            tmpNode = tmpNode.linkedNode;

        }
        System.out.println(tmp.substring(0, tmp.length()-1));

        //searchResultsList 에서 부분배열을 이어붙여야됨.
    }

    private static boolean searchAnswer(AVLNode<ExtendedStr> prevNode, List<AVLNode<ExtendedStr>> searchResultsList, int subStringIndex, int back) {
        if (searchResultsList.size() == 1) return true;
        AVLNode<ExtendedStr> curNode = searchResultsList.get(subStringIndex);//탐색 실패
        if (subStringIndex == searchResultsList.size() - 1) { // 마지막에는 back 따져서 이어붙여줄 것.
            while (curNode != null) {
                //System.out.println( prevNode + " " + curNode);
                //System.out.println( curNode.item.getPosX() == prevNode.item.getPosX() && prevNode.item.getPosY() + 6 - back == curNode.item.getPosY() );
                if (curNode.item.getPosX() > prevNode.item.getPosX()) {
                    break;
                }
                if (curNode.item.getPosX() == prevNode.item.getPosX() && prevNode.item.getPosY() + 6 - back == curNode.item.getPosY()) {
                    return true;
                }
                curNode = curNode.linkedNode;
            }

        } else {
            while (curNode != null) {
                if (curNode.item.getPosX() > prevNode.item.getPosX()) {
                    break;
                }
                if (curNode.item.getPosX() == prevNode.item.getPosX() && prevNode.item.getPosY() + 6 == curNode.item.getPosY()) {
                    return searchAnswer(curNode, searchResultsList, subStringIndex + 1, back);
                }
                curNode = curNode.linkedNode;
            }

        }
        return false; //탐색 실패
    }

    /*private static void handleNotFound(List<AVLNode<ExtendedStr>> searchResultsList, ExtendedStr extendedStr) {
        if (hashTable.get(extendedStr.hashCode()) == null) { //부분배열이 없으면? 원본배열도 없다!
            System.out.println("(0, 0)");
            return;
        }
        AVLNode<ExtendedStr> searchResult = hashTable.get(extendedStr.hashCode()).search(extendedStr);
        if (searchResult.item == null) { //NIL CASE: NOT FOUND 부분배열이 없으면? 원본배열도 없다!
            System.out.println("(0, 0)");
            return;
        }
        searchResultsList.add(searchResult);
    }
    */
    private static void printData(String inputData) {
        if (hashTable.get(Integer.parseInt(inputData)) == null) {
            //System.out.println(Integer.parseInt(inputData));
            System.out.println("EMPTY");
            return;
        }
        List<ExtendedStr> extendedStrList = hashTable.get(Integer.parseInt(inputData)).getPreOrderNode();
        StringBuilder tmp = new StringBuilder();
        for (ExtendedStr extendedStr : extendedStrList) {
            tmp.append(extendedStr.item + " ");
            /*if(extendedStr.equals(extendedStrList.get(extendedStrList.size()-1))) {
                System.out.print(extendedStr.item);
            }
            else System.out.print(extendedStr.item + " ");*/
        }
        System.out.println(tmp.substring(0, tmp.length()-1));
    }


}
