(ns eliza.hypergraphdb
  (:gen-class)
  (:import [org.hypergraphdb HGEnvironment HyperGraph HGHandle
                             HGQuery$hg HGPlainLink HGValueLink])
  (:import [org.hypergraphdb.algorithms DefaultALGenerator HGTraversal HGBreadthFirstTraversal])
  (:import [org.hypergraphdb.handle UUIDHandleFactory]))

;TODO: should not be hardcoded in
(def location "./db")
(def graph (atom nil))
(def factory (atom nil))


(defn initialize []
  (reset! graph (HGEnvironment/get location))
  (reset! factory (new UUIDHandleFactory)))

(defn hg-close []
  (.close @graph))

(defn hg-get-node [handle]
  (.get @graph handle))

(defn hg-get-link-value [handle]
  (.getValue (.get @graph handle)))

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

(defn hg-replace-link [handle coll value]
  ;Hypergraphdb cannot figure out clojure vectors. Cast to HGHandle Java array
  (.replace @graph handle (new HGValueLink value (into-array HGHandle coll) )))

(defn hg-change-link-value [handle value]
  (.setValue (.get @graph handle) value))

; Input:    two <HGHandle> nodes
; Output:   <HGHandle> link
; Purpose:  establish a HGPlainLink between two nodes
; TODO:     This is legacy, remove
;(defn hg-add-link [node1 node2]
;  (.add @graph (new HGPlainLink (into-array HGHandle [node1 node2]))))

; Current implementation
(defn hg-add-link [coll value]
  (let [weak-handle (.add @graph (new HGValueLink value (into-array HGHandle coll)))]
    (.getPersistentHandle @graph weak-handle)))

; Input:    vector of node HGHandles
; Output:   vector of link HGHandles
; Purpose:  establish a HGPlainLink between every
;           node in the vector
; TODO:     This is legacy, remove in the future. Interesting code though.
;(defn hg-add-links [coll]
;  (let [parted-coll (partition 2 1 coll)]
;    (map #(.getPersistentHandle @graph (hg-add-link (first %) (second %))) parted-coll)))

(defn hg-incidence-set [concept-handle]
  (.getIncidenceSet @graph concept-handle))

(defn hg-incidence-byte-set [concept-handle]
  (let [coll (hg-incidence-set concept-handle)]
    ; We can't use lazy seq when we use clojure.set/intersection method
    ; Which means we have to cast it to a string to compare. Byte Arrays don't work compare well.
    (into #{} (map #(str %) coll))))

(defn hg-link-arity [link-handle]
  (let [link (.get @graph link-handle)]
    (.getArity link)))

(defn hg-make-handle-string [string-handle]
  (.makeHandle @factory string-handle))

; Without .getPersistentHandle, the first item returns as a weak handle.
; However the next item seems to be persistent. Not sure why.
(defn hg-target-set [link-handle]
  (let [link  (.get @graph link-handle)
        arity (hg-link-arity link-handle)]
    (map #(.getPersistentHandle @graph (.getTargetAt link %)) (range arity))))

; Input:    vector of node HGHandles
; Purpose:  remove all referenced nodes
; TODO:     removal of nodes should not remove their hyperarcs
(defn hg-remove-nodes [coll]
  (doseq [node coll]
    (.remove @graph node)))

; Purpose:  add an array of strings to the hypergraph
; TODO:     remove this in the future
;(defn hg-add-nodes-links [nodes]
;      (hg-add-links (hg-add-nodes nodes)))

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