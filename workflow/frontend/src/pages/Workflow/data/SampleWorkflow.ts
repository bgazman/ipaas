const workflow = {
  nodes: [
    { id: "A", position: { x: 0, y: 0 } },
    { id: "B", position: { x: 100, y: 0 } },
    { id: "C", position: { x: 50, y: 100 } },
    { id: "D", position: { x: 100, y: 100 } },
    { id: "E", position: { x: 150, y: 100 } },
    { id: "F", position: { x: 100, y: 200 } }
  ],
  edges: [
    { id: "e1", source: "A", target: "B" },
    { id: "e2", source: "B", target: "C" },
    { id: "e3", source: "B", target: "D" },
    { id: "e4", source: "B", target: "E" },
    { id: "e5", source: "C", target: "F" },
    { id: "e6", source: "D", target: "F" }
  ]
 }