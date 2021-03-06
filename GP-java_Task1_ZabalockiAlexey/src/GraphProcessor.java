import java.util.*;

public class GraphProcessor {
    static class AdjListNode {
        int vertex;
        int weight;

        AdjListNode(int vertex, int weight) {
            this.vertex = vertex;
            this.weight = weight;
        }

        int getVertex() {
            return vertex;
        }

        int getWeight() {
            return weight;
        }
    }

    static class Graph {
        int verticesNum;
        ArrayList<ArrayList<AdjListNode>> adj;

        Graph(int verticesNum) {
            this.verticesNum = verticesNum;
            adj = new ArrayList<>(verticesNum);

            for (int i = 0; i < verticesNum; i++) {
                adj.add(new ArrayList<>());
            }
        }

        void addEdge(int u, int v, int weight) {
            AdjListNode node = new AdjListNode(v, weight);
            adj.get(u).add(node);
        }

        int findLongest(int u) {
            ArrayList<AdjListNode> adjListNodes = adj.get(u);
            int max = Integer.MIN_VALUE;
            for (AdjListNode adjListNode : adjListNodes) {
                if (max < adjListNode.getWeight()) {
                    max = adjListNode.getWeight();
                }
            }
            return max;
        }

        void topologicalSortUtil(int v, boolean[] visited,
                                 Stack<Integer> stack) {
            visited[v] = true;

            for (int i = 0; i < adj.get(v).size(); i++) {
                AdjListNode node = adj.get(v).get(i);
                if (!visited[node.getVertex()]) {
                    topologicalSortUtil(node.getVertex(), visited, stack);
                }
            }

            stack.push(v);
        }

        int[] longestPath(int s, int weight) {
            Stack<Integer> stack = new Stack<>();
            int[] dist = new int[verticesNum];

            boolean[] visited = new boolean[verticesNum];
            for (int i = 0; i < verticesNum; i++) {
                visited[i] = false;
            }

            for (int i = 0; i < verticesNum; i++) {
                if (!visited[i]) {
                    topologicalSortUtil(i, visited, stack);
                }
            }

            for (int i = 0; i < verticesNum; i++) {
                dist[i] = Integer.MIN_VALUE;
            }

            dist[s] = weight;

            while (!stack.isEmpty()) {
                int u = stack.peek();
                stack.pop();

                if (dist[u] != Integer.MIN_VALUE) {
                    for (int i = 0; i < adj.get(u).size(); i++) {
                        AdjListNode node = adj.get(u).get(i);
                        if (dist[node.getVertex()] < dist[u] + node.getWeight()) {
                            dist[node.getVertex()] = dist[u] + node.getWeight();
                        }
                    }
                }
            }
            return dist;
        }
    }

    public static void main(String[] args) {
        GraphProcessor.Graph g = new Graph(9);
        g.addEdge(0, 3, 3);
        g.addEdge(3, 4, 3);
        g.addEdge(3, 6, 4);
        g.addEdge(0, 5, 1);
        g.addEdge(5, 8, 4);
        g.addEdge(5, 6, 4);
        g.addEdge(1, 3, 3);
        g.addEdge(1, 5, 1);
        g.addEdge(1, 7, 6);
        g.addEdge(7, 6, 4);
        g.addEdge(7, 8, 4);
        g.addEdge(2, 7, 6);
        g.addEdge(2, 5, 1);

        Map<Integer, String> edgeToPosition = new HashMap();
        edgeToPosition.put(0, "[0][0]");
        edgeToPosition.put(1, "[1][0]");
        edgeToPosition.put(2, "[2][0]");
        edgeToPosition.put(3, "[0][1]");
        edgeToPosition.put(4, "[0][2]");
        edgeToPosition.put(5, "[1][1]");
        edgeToPosition.put(6, "[1][2]");
        edgeToPosition.put(7, "[2][1]");
        edgeToPosition.put(8, "[2][2]");

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(0);

        List<int[]> resultArray = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            resultArray.add(g.longestPath(i, list.get(i)));
        }

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < g.verticesNum; i++) {
            int max = Integer.MIN_VALUE;
            for (int j = 0; j < list.size(); j++) {
                int value = resultArray.get(j)[i];
                if (max < value) {
                    max = value;
                }
            }
            result.add(max);
        }

        System.out.println("all longest paths tree from initialVertex: ");

        for (int i = 0; i < result.size(); i++) {
            System.out.println("initialVertex -> " + edgeToPosition.get(i)+ "= " + result.get(i));
        }
        int max = 0;
        int max_index = 0;
        for (Integer integer : result) {
            if (integer > max) {
                max = integer;
                max_index = max;
            }
        }
        System.out.println("longest path tree from vertex 'initialVertex' = " + max_index);
    }
}



