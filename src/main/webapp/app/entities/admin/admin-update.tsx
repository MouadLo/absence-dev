import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './admin.reducer';
import { IAdmin } from 'app/shared/model/admin.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAdminUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AdminUpdate = (props: IAdminUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { adminEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/admin');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...adminEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="intranetApp.admin.home.createOrEditLabel">
            <Translate contentKey="intranetApp.admin.home.createOrEditLabel">Create or edit a Admin</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : adminEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="admin-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="admin-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="loginLabel" for="admin-login">
                  <Translate contentKey="intranetApp.admin.login">Login</Translate>
                </Label>
                <AvField
                  id="admin-login"
                  type="text"
                  name="login"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="mdpLabel" for="admin-mdp">
                  <Translate contentKey="intranetApp.admin.mdp">Mdp</Translate>
                </Label>
                <AvField
                  id="admin-mdp"
                  type="text"
                  name="mdp"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="emailLabel" for="admin-email">
                  <Translate contentKey="intranetApp.admin.email">Email</Translate>
                </Label>
                <AvField
                  id="admin-email"
                  type="text"
                  name="email"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="nomLabel" for="admin-nom">
                  <Translate contentKey="intranetApp.admin.nom">Nom</Translate>
                </Label>
                <AvField
                  id="admin-nom"
                  type="text"
                  name="nom"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="prenomLabel" for="admin-prenom">
                  <Translate contentKey="intranetApp.admin.prenom">Prenom</Translate>
                </Label>
                <AvField
                  id="admin-prenom"
                  type="text"
                  name="prenom"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="telLabel" for="admin-tel">
                  <Translate contentKey="intranetApp.admin.tel">Tel</Translate>
                </Label>
                <AvField
                  id="admin-tel"
                  type="text"
                  name="tel"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/admin" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  adminEntity: storeState.admin.entity,
  loading: storeState.admin.loading,
  updating: storeState.admin.updating,
  updateSuccess: storeState.admin.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AdminUpdate);
