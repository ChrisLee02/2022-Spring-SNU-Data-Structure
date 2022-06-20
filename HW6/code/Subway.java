import java.io.*;
import java.util.*;

public class Subway {
    public static void main(String args[]) throws Exception {
        // args[0]로 파일 경로 주어짐.

        //System.out.println(args[0]);

        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, "UTF-8"));


        Map<String, Station> graph = new HashMap<>();
        Map<String, List<Station>> nameToStations = new HashMap<>();

        try {
            importGraph(args[0], graph, nameToStations);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (String key : graph.keySet()) {
            Collections.sort(graph.get(key).edges);
        }
        /*System.out.println(graph);
        System.out.println(nameToStations);*/


        BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
        while (true) {
            try {
                String input = br.readLine();

                if (input.compareTo("QUIT") == 0)
                    break;

                command(input, graph, nameToStations);

            } catch (IOException e) {
                System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
            }
        }
    }

    private static void initGraph(Map<String, Station> graph) {
        for (String key : graph.keySet()) {
            graph.get(key).cost = Station.INF;
            graph.get(key).prev = null;
        }
    }
    //dijkstra 실행 전 cost 및 prev 초기화작업.

    private static void dijkstra(Station rootStation, Map<String, Station> graph) {
        /*initGraph(graph);
        Set<Station> SetV = new HashSet<>();
        Heap<Station> HeapV = new Heap<>(graph.size());
        rootStation.cost = 0;
        for (String key : graph.keySet()) {
            SetV.add(graph.get(key));
            HeapV.insert(graph.get(key));
        }
        // 거리는 정수라는걸 이용한다면, 환승에 적당히 소수점만큼의 패널티를 부여해서
        while (!SetV.isEmpty()) {
            Station station = HeapV.deleteMin();
            SetV.remove(station);
            for (Edge edge : station.edges) {
                if (SetV.contains(edge.arrivalPoint) && station.cost + edge.distance < edge.arrivalPoint.cost) {
                    // if 문에서 contains 조건이 없어도 알고리즘 자체는 정상 동작하는가?
                    edge.arrivalPoint.cost = station.cost + edge.distance;
                    edge.arrivalPoint.prev = station;
                    HeapV.buildHeap();
                }
            }

        }*/
        //구버전 백업

        initGraph(graph);
        Set<Station> SetV = new HashSet<>();
        Heap<Station> HeapV = new Heap<>(graph.size());
        rootStation.cost = 0;
        rootStation.transfer = 0;
        for (String key : graph.keySet()) {
            SetV.add(graph.get(key));
            HeapV.insert(graph.get(key));
        }
        // 거리는 정수라는걸 이용한다면, 환승에 적당히 소수점만큼의 패널티를 부여해서
        // ㄴㄴ, thats bullshit
        while (!SetV.isEmpty()) {
            Station station = HeapV.deleteMin();
            SetV.remove(station);
            /*System.out.println(station);
            System.out.println(station.edges);*/
            for (Edge edge : station.edges) {
                if (SetV.contains(edge.arrivalPoint)) {
                    // if 문에서 contains 조건이 없어도 알고리즘 자체는 정상 동작하는가?
                    if (station.cost + edge.distance < edge.arrivalPoint.cost) {
                        // 귀납적으로 여기서 업데이트 로직 넣기
                        edge.arrivalPoint.cost = station.cost + edge.distance;
                        edge.arrivalPoint.prev = station;
                        if(edge.arrivalPoint.name.equals(station.name)){
                            edge.arrivalPoint.transfer = station.transfer + 1;
                        }
                        else {
                            edge.arrivalPoint.transfer = station.transfer;
                        }
                        HeapV.buildHeap();
                    }
                    else if(station.cost + edge.distance == edge.arrivalPoint.cost ) {
                        //이부분 수정해야됨. 인접한 부분만으로 greedy하게 판단하지 말고, station에 이전 환승 횟수정보 저장하는 식으로,,
                        //이전 환승횟수 & 지금 환승 중인지 이 두가지를 여기서 판단해줘야 할 듯,,
                        if(station.name.equals(edge.arrivalPoint.name)) {
                            if(station.transfer + 1 < edge.arrivalPoint.transfer) {
                                edge.arrivalPoint.cost = station.cost + edge.distance;
                                edge.arrivalPoint.prev = station;
                                edge.arrivalPoint.transfer = station.transfer + 1;
                                HeapV.buildHeap();
                            }
                        }
                        else {
                            if(station.transfer < edge.arrivalPoint.transfer) {
                                edge.arrivalPoint.cost = station.cost + edge.distance;
                                edge.arrivalPoint.prev = station;
                                edge.arrivalPoint.transfer = station.transfer;
                                HeapV.buildHeap();
                            }
                        }

                    }
                }
            }

        }

    }

    private static Route makeRoute(Station endPoint) {
        Route route = new Route();
        route.cost = endPoint.cost;
        Station curStation = endPoint;
        while (curStation != null) {
            // System.out.println(curStation);
            route.route.add(0, curStation.name);
            curStation = curStation.prev;
        }
        return route;
    }

    private static void command(String input, Map<String, Station> graph, Map<String, List<Station>> nameToStations) {
        /*String[] parsed = input.split(" ");
        //System.out.println(parsed[0]);
        List<Station> startPoints = nameToStations.get(parsed[0]);

        List<Triple<Station, Station, Integer>> cases = new ArrayList<>();
        //System.out.println("DD");
        for (Station startPoint : startPoints) {
            dijkstra(startPoint, graph);
            List<Station> endPoints = nameToStations.get(parsed[1]);
            Collections.sort(endPoints);
            cases.add(new Triple<>(startPoint, endPoints.get(0), endPoints.get(0).cost));
        }
        Collections.sort(cases);
        //System.out.println(cases);
        Station startPoint = cases.get(0).first;
        Station endPoint = cases.get(0).second;

        dijkstra(startPoint, graph);
        //System.out.println(graph);
        Route optimalRoute = makeRoute(endPoint);
        //System.out.println(optimalRoute.route);
        optimalRoute.printRoute();*/
        //구버전 백업

        String[] parsed = input.split(" ");
        //System.out.println(parsed[0]);
        List<Station> startPoints = nameToStations.get(parsed[0]);

        List<Route> routes = new ArrayList<>();
        //System.out.println("DD");
        for (Station startPoint : startPoints) {
            dijkstra(startPoint, graph);
            List<Station> endPoints = nameToStations.get(parsed[1]);
            for (Station endPoint : endPoints) {
                Route route = makeRoute(endPoint);
                route.transfer = endPoint.transfer;
                routes.add(route);
            }
        }
        Collections.sort(routes);

        Route optimalRoute = routes.get(0);
        /*int optimal = optimalRoute.cost;
        for(Route route: routes) {
            if(route.cost > optimal) break;

            route.printRoute();
        }*/
        optimalRoute.printRoute();
        // 신버전 백업
    }


    private static void addStation(Station station, Map<String, Station> graph, Map<String, List<Station>> nameToStations) {
        graph.put(station.ID, station); // 추가
        if (nameToStations.get(station.name) == null) {
            nameToStations.put(station.name, new LinkedList<>());
            nameToStations.get(station.name).add(station);
        } else {
            List<Station> stationList = nameToStations.get(station.name);
            for (Station station1 : stationList) {
                station1.edges.add(0, new Edge(station, 5));
                station1.edges.get(0).isTransfer = true;
                station.edges.add(0, new Edge(station1, 5));
                station.edges.get(0).isTransfer = true;
            }
            stationList.add(station);
        } // 환승처리
    }

    private static void importGraph(String dataPath, Map<String, Station> graph, Map<String, List<Station>> nameToStations) throws Exception {
        /*FileInputStream input=new FileInputStream(dataPath);
        System.out.println("DD");
        InputStreamReader reader=new InputStreamReader(input,"UTF-8");
        System.out.println("DD");
        Scanner scanner = new Scanner(reader);
        System.out.println("DD");*/

        FileInputStream input = new FileInputStream(dataPath);
        InputStreamReader reader = new InputStreamReader(input, "UTF-8");
        BufferedReader in = new BufferedReader(reader);


        String line;
        while (true) {
            line = in.readLine();
            if (line == null || line.equals("")) break;

            String[] parsed = line.split(" ");
            Station station = new Station(parsed[0], parsed[1], parsed[2]);

            addStation(station, graph, nameToStations);
        }

        while (true) {
            line = in.readLine();
            if (line == null || line.equals("")) break;

            String[] parsed = line.split(" ");
            Station station = graph.get(parsed[0]);
            Edge edge = new Edge(graph.get(parsed[1]), Integer.parseInt(parsed[2]));
            station.edges.add(edge);
        }
    }


    // 고민할 포인트
    // 임의의 문자열로 표현되는 고유 번호를 어떻게 인접행렬에 이용할 것인지.
    // 1. 그래프 표현은 1차원 해쉬맵 + 각 요소마다 1차원 배열
    //

}
