import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";
import EditProfileForm from "./forms/EditProfileForm";
import React from "react";

function EditProfileModal(props) {
  return (
    <Modal
      {...props}
      size="lg"
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          Edit profile
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <EditProfileForm data={props.data} onHide={props.onHide} />
      </Modal.Body>
    </Modal>
  );
}

export default EditProfileModal;
