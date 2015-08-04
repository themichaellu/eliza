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

(defn hg-temp-get [handle]
  (.get @graph handle))

; Input:    type 
; Output    <ItemType> vector
; Purpose:  get all nodes of a specific type 
(defn hg-get-all [get-type]
  (HGQuery$hg/getAll @graph (HGQuery$hg/type get-type)))

; Input:    type 
; Output    <HGHandle> vector
; Purpose:  get all the handles of nodes of a specific type
(defn hg-find-all [get-type]
  (HGQuery$hg/findAll @graph (HGQuery$hg/type get-type)))

; Input:    collection of items
; Output:   <HGHandle> vector for corresponding items
; Purpose:  add vector of items into graph
(defn hg-add-items [coll]
  (map #(.add @graph %) coll))

; Input:    two <HGHandle> items
; Output:   <HGHandle> link
; Purpose:  establish a HGPlainLink between two items
(defn hg-add-link [item1 item2]
  (.add @graph (new HGPlainLink (into-array HGHandle [item1 item2]))))

; Input:    vector of item HGHandles
; Output:   vector of link HGHandles
; Purpose:  establish a HGPlainLink between every
;           item in the vector
(defn hg-add-links [coll]
  (let [parted-coll (partition 2 1 coll)]
    (map #(hg-add-link (first %) (second %)) parted-coll)))

; Input:    vector of item HGHandles
; Purpose:  remove all referenced items
(defn hg-remove-items [coll]
  (doseq [item coll]
    (.remove @graph item)))

; Purpose:  add an array of strings to the hypergraph
; TODO:     remove this in the future
(defn hg-temp-add [items]
      (hg-add-links (hg-add-items items)))

; Purpose:  remove all string nodes from hypergraph
; TODO:     remove this in the future
(defn hg-temp-remove []
  (hg-remove-items (hg-find-all String)))

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

