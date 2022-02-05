import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GraphProcessor {
    static class AdjListNode {
        int vertex;
        int weight;

        AdjListNode(int vertex, int weight)
        {
            this.vertex = vertex;
            this.weight = weight;
        }
        int getVertex() { return vertex; }
        int getWeight() { return weight; }
    }

    static class Graph {
        int verticesNum;
        ArrayList<ArrayList<AdjListNode>> adj;

        Graph(int verticesNum)
        {
            this.verticesNum = verticesNum;
            adj = new ArrayList<>(verticesNum);

            for(int i = 0; i < verticesNum; i++){
                adj.add(new ArrayList<>());
            }
        }

        void addEdge(int u, int v, int weight)
        {
            AdjListNode node = new AdjListNode(v, weight);
            adj.get(u).add(node);
        }

        void topologicalSortUtil(int v, boolean[] visited,
                                 Stack<Integer> stack)
        {
            visited[v] = true;

            for (int i = 0; i<adj.get(v).size(); i++) {
                AdjListNode node = adj.get(v).get(i);
                if (!visited[node.getVertex()]){
                    topologicalSortUtil(node.getVertex(), visited, stack);
                }
            }

            stack.push(v);
        }

        void longestPath(int s, int weight)
        {
            Stack<Integer> stack = new Stack<>();
            int[] dist = new int[verticesNum];

            boolean[] visited = new boolean[verticesNum];
            for (int i = 0; i < verticesNum; i++){
                visited[i] = false;
            }

            for (int i = 0; i < verticesNum; i++){
                if (!visited[i]){
                    topologicalSortUtil(i, visited, stack);
                }
            }

            for (int i = 0; i < verticesNum; i++){
                dist[i] = Integer.MIN_VALUE;
            }

            dist[s] = weight;

            while (!stack.isEmpty())
            {
                int u = stack.peek();
                stack.pop();

                if (dist[u] != Integer.MIN_VALUE)
                {
                    for (int i = 0; i<adj.get(u).size(); i++)
                    {
                        AdjListNode node = adj.get(u).get(i);
                        if (dist[node.getVertex()] < dist[u] + node.getWeight()){
                            dist[node.getVertex()] = dist[u] + node.getWeight();
                        }
                    }
                }
            }

            for (int i = 0; i < verticesNum; i++) {
                if (dist[i] == Integer.MIN_VALUE) {
                    continue;
                } else {
                    System.out.print("index: " + i + " = " + (dist[i]) + ";\n");
                }
            }
        }
    }

    public static void main(String[] args)
    {
        GraphProcessor.Graph g = new Graph(9);
        g.addEdge(0, 3, 3);
        g.addEdge(3, 4, 3);             //vertex indexes: 0->[0][0]
        g.addEdge(3, 6, 4);             //                1->[1][0]
        g.addEdge(0, 5, 1);             //                2->[2][0]
        g.addEdge(5, 8, 4);             //                3->[0][1]
        g.addEdge(5, 6, 4);             //                4->[0][2]
        g.addEdge(1, 3, 3);             //                5->[1][1]
        g.addEdge(1, 5, 1);             //                6->[1][2]
        g.addEdge(1, 7, 6);             //                7->[2][1]
        g.addEdge(7, 6, 4);             //                8->[2][2]
        g.addEdge(7, 8, 4);
        g.addEdge(2, 7, 6);
        g.addEdge(2, 5, 1);

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(0);

        for (int i = 0; i < list.size(); i++) {
            System.out.print("The longest distances from source vertex " + i + "\n");
            g.longestPath(i, list.get(i));
        }

    }
}
