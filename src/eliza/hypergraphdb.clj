(ns eliza.hypergraphdb
  (:gen-class)
  (:import [org.hypergraphdb HGEnvironment HyperGraph HGHandle HGQuery$hg HGPlainLink])
  (:import [org.hypergraphdb.algorithms DefaultALGenerator HGTraversal HGBreadthFirstTraversal]))

;TODO: should not be hardcoded in
(def location "./db")
(def graph (atom nil))


(defn initialize []
  (reset! graph (HGEnvironment/get location)))

(defn hg-close []
  (.close @graph))

(defn hg-get-node [handle]
  (.get @graph handle))

; Input:    type 
; Output    <AtomType> vector
; Purpose:  get all nodes of a specific type 
(defn hg-get-all [get-type]
  (HGQuery$hg/getAll @graph (HGQuery$hg/type get-type)))

; Input:    type 
; Output    <HGHandle> vector
; Purpose:  get all the handles of nodes of a specific type
(defn hg-find-all [get-type]
  (HGQuery$hg/findAll @graph (HGQuery$hg/type get-type)))

; Input:    collection of nodes
; Output:   <HGHandle> vector for corresponding nodes
; Purpose:  add vector of nodes into graph
(defn hg-add-nodes [coll]
  (map #(.getPersistentHandle @graph (.add @graph %)) coll))

(defn hg-replace-node [handle atom]
  (.replace @graph handle atom))

; Input:    two <HGHandle> nodes
; Output:   <HGHandle> link
; Purpose:  establish a HGPlainLink between two nodes
(defn hg-add-link [node1 node2]
  (.add @graph (new HGPlainLink (into-array HGHandle [node1 node2]))))

; Input:    vector of node HGHandles
; Output:   vector of link HGHandles
; Purpose:  establish a HGPlainLink between every
;           node in the vector
(defn hg-add-links [coll]
  (let [parted-coll (partition 2 1 coll)]
    (map #(.getPersistentHandle @graph (hg-add-link (first %) (second %))) parted-coll)))

; Input:    vector of node HGHandles
; Purpose:  remove all referenced nodes
; TODO:     removal of nodes should not remove their hyperarcs
(defn hg-remove-nodes [coll]
  (doseq [node coll]
    (.remove @graph node)))

; Purpose:  add an array of strings to the hypergraph
; TODO:     remove this in the future
(defn hg-add-nodes-links [nodes]
      (hg-add-links (hg-add-nodes nodes)))


; Purpose:  remove all string nodes from hypergraph
; TODO:     remove this in the future
(defn hg-remove-type [type]
  (hg-remove-nodes (hg-find-all type)))

; Input:    HGHandle of start node, type of nodes in traversal
; Output:   <HGHandle> vector
; Purpose:  Breadth-First-Traversal given a head node
(defn hg-bft [node get-type]
  (let [response (transient [(.getHandle @graph node)]) 
        alGen (new DefaultALGenerator @graph (HGQuery$hg/type HGPlainLink) (HGQuery$hg/type get-type) false true false)
        trav (new HGBreadthFirstTraversal (.getHandle @graph node) alGen)]
    (while (= true (.hasNext trav))
      (conj! response (.getSecond (.next trav))))
      (persistent! response)))
